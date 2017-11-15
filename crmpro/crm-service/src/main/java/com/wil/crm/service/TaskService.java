package com.wil.crm.service;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Task;

import java.util.List;

/**
 * Created by wil on 2017/11/14.
 */
public interface TaskService {

    /**
     * 查找当前account的所有task
     * @param account
     * @return
     */
    List<Task> findAllTaskList(Account account);

    List<Task> findTodoTaskList(Account account);

    List<Task> findOverTimeTaskList(Account account);

    /**
     * 新增任务
     * @param task
     */
    void saveTask(Task task);

    Task findById(Integer id);


    void deleteTaskById(Integer id);


    void changeStateToDone(Task task);

    void changeStateToDo(Task task);
}
