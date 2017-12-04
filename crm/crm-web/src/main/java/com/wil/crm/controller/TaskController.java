package com.wil.crm.controller;

import com.wil.crm.controller.exception.ForbiddenException;
import com.wil.crm.controller.exception.NotFoundException;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Task;
import com.wil.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wil on 2017/11/14.
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/todo")
    public String todoList(Model model, HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Task> taskList = taskService.findTodoTaskList(account);
        model.addAttribute("taskList", taskList);
        return "task/todo";
    }
    @GetMapping("/over")
    public String overTimeList(Model model, HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Task> taskList = taskService.findOverTimeTaskList(account);
        model.addAttribute("taskList", taskList);
        return "task/over";
    }

    @GetMapping("/all")
    public String allList(Model model, HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Task> taskList = taskService.findAllTaskList(account);
        model.addAttribute("taskList", taskList);
        return "task/list";
    }

    @GetMapping("/new")
    public String newTask(Integer saleId,Integer customerId, Model model) {
        model.addAttribute("saleId", saleId);
        model.addAttribute("customerId", customerId);
        return "task/new";
    }

    /**
     * 新增待办事项
     * @param task
     * @return
     */
    @PostMapping("/new")
    public String newTask(Task task) {
        taskService.saveTask(task);
        return "redirect:/task/todo";
    }

    /**
     * 删除待办事项
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/{id:\\d+}/del")
    public String deleteTask(@PathVariable Integer id, HttpSession session) {

        Task task = checkTask(session,id);
        taskService.deleteTaskById(id);
        return "redirect:/task/todo";
    }

    /**
     * 将task状态改变为已完成
     * @return
     */
    @GetMapping("/{id:\\d+}/state/done")
    public String changeStateDone(@PathVariable Integer id, HttpSession session) {
        Task task = checkTask(session, id);
        taskService.changeStateToDone(task);
        return "redirect:/task/all";
    }

    @GetMapping("/{id:\\d+}/state/todo")
    public String changeStateTodo(@PathVariable Integer id, HttpSession session) {
        Task task = checkTask(session, id);
        taskService.changeStateToDo(task);
        return "redirect:/task/all";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editTask(@PathVariable Integer id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "task/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editTask(@PathVariable Integer id, Task task) {
        Task newTask = taskService.findById(id);
        newTask.setTitle(task.getTitle());
        newTask.setRemindTime(task.getRemindTime());
        newTask.setFinishTime(task.getFinishTime());
        newTask.setCustomerId(task.getCustomerId());
        newTask.setSaleId(task.getSaleId());
        newTask.setDone(task.getDone());
        taskService.editTask(newTask);
        return "redirect:/task/all";
    }


    private Task checkTask(HttpSession session, Integer id) {
        Account account = getCurrentAccount(session);

        Task task = taskService.findById(id);
        if(task == null) {
            throw new NotFoundException();
        }
        if(!account.getId().equals(task.getAccountId())) {
            throw new ForbiddenException();
        }
        return task;
    }




}
