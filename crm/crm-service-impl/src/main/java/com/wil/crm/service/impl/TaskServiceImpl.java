package com.wil.crm.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.entity.Task;
import com.wil.crm.example.TaskExample;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.jobs.SendMessageJob;
import com.wil.crm.mapper.CustomerMapper;
import com.wil.crm.mapper.SaleChanceMapper;
import com.wil.crm.mapper.TaskMapper;
import com.wil.crm.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * Created by wil on 2017/11/14.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final static Integer TODO_STATE = 0;
    private final static Integer DONE_STATE = 1;

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 查找当前account的所有task
     * @param account
     * @return
     */
    @Override
    public List<Task> findAllTaskList(Account account) {

        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(account.getId());
        taskExample.setOrderByClause("id desc");
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        for(Task t : taskList) {
            if(t.getCustomerId() != null) {
                Customer customer = customerMapper.selectByPrimaryKey(t.getCustomerId());
                t.setCustomer(customer);
            }
            if(t.getSaleId() != null) {
                SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(t.getSaleId());
                t.setSaleChance(saleChance);
            }
        }
        return taskList;
    }

    /**
     * 查找未来未完成的事项
     * @param account
     * @return
     */
    @Override
    public List<Task> findTodoTaskList(Account account) {

        List<Task> taskList = Lists.newArrayList(Collections2.filter(findAllTaskList(account),
                new Predicate<Task>() {
            @Override
            public boolean apply(@Nullable Task task) {
                return (task.getDone().equals(TODO_STATE) && !task.isOverTime());
            }
        }));

        return taskList;
    }

    /**
     * 查找过去未完成的事项
     * @param account
     * @return
     */
    @Override
    public List<Task> findOverTimeTaskList(Account account) {

        List<Task> taskList = Lists.newArrayList(Collections2.filter(findAllTaskList(account),
                new Predicate<Task>() {
                    @Override
                    public boolean apply(@Nullable Task task) {
                        return (task.getDone().equals(TODO_STATE) && task.isOverTime());
                    }
                }));

        return taskList;
    }

    /**
     * 新增任务
     * @param task
     */
    @Override
    @Transactional
    public void saveTask(Task task) {

        task.setCreateTime(new Date());
        task.setDone(TODO_STATE);
        taskMapper.insert(task);
        logger.info("新增待办事项:{}",task.getTitle());

        //添加调度任务
        addTaskQuartzJob(task);
    }

    @Override
    public Task findById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除任务
     * @param id
     */
    @Override
    @Transactional
    public void deleteTaskById(Integer id) {
        Task task = taskMapper.selectByPrimaryKey(id);
        taskMapper.deleteByPrimaryKey(id);
        //删除定时任务
        deleteTaskQuartzJob(task);

    }

    /**
     * 修改任务状态到完成
     * @param task
     */
    @Override
    @Transactional
    public void changeStateToDone(Task task) {
        task.setDone(DONE_STATE);
        taskMapper.updateByPrimaryKey(task);
        //删除定时任务
        deleteTaskQuartzJob(task);

    }

    /**
     * 修改任务状态为未完成
     * @param task
     */
    @Override
    @Transactional
    public void changeStateToDo(Task task) {
        task.setDone(TODO_STATE);
        taskMapper.updateByPrimaryKey(task);
        //删除定时任务
        deleteTaskQuartzJob(task);

    }

    /**
     * 修改提醒任务
     * @param task
     */
    @Override
    public void editTask(Task task) {
        deleteTaskQuartzJob(task);
        taskMapper.updateByPrimaryKey(task);
        addTaskQuartzJob(task);


    }

    private void addTaskQuartzJob(Task task) {
        //添加调度任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("accountId", task.getAccountId());
            jobDataMap.put("message", task.getTitle());

            JobDetail jobDetail = JobBuilder
                    .newJob(SendMessageJob.class)
                    .setJobData(jobDataMap)
                    .withIdentity("taskId:"+task.getId(), "sendMessageGroup")
                    .build();

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());

            StringBuilder cron = new StringBuilder();
            cron.append("0").append(" ")
                    .append(dateTime.getMinuteOfHour()).append(" ")
                    .append(dateTime.getHourOfDay()).append(" ")
                    .append(dateTime.getDayOfMonth()).append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ").append(dateTime.getYear());

            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(scheduleBuilder)
                    .build();

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (Exception ex) {
                throw new ServiceException(ex,"添加定时任务异常");
            }
        }
    }

    /**
     * 删除定时任务
     * @param task
     */
    private void deleteTaskQuartzJob(Task task) {
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            try {
                scheduler.deleteJob(new JobKey("taskId"+task.getId(),"sendMessageGroup"));
                logger.info("删除定时任务 taskId:{} groupName:{}", task.getId(), "sendMessageGroup");
            } catch (SchedulerException e) {
                throw new ServiceException(e, "删除定时任务异常");
            }
        }
    }


}
