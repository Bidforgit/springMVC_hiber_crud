package com.anuar.controllers;


import com.anuar.dao.TaskDAO;
import com.anuar.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskDAO taskDAO;

    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks",taskDAO.getAllTasks());
        return "index";
    }

    @GetMapping("/filter")
    public String filterByStatus(@RequestParam(name = "filter", required = false) String filter, Model model) {
        List<Task> filteredTasks;

        if(filter.equals("open")) {
            filteredTasks = taskDAO.getTasksByFilter("open");
        } else if (filter.equals("closed")) {
            filteredTasks = taskDAO.getTasksByFilter("closed");
        }else {
            filteredTasks = taskDAO.getAllTasks();
        }
        model.addAttribute("selectedFilter", filter);
        model.addAttribute("tasks",filteredTasks);

        return "index";
    }

    @GetMapping("/{id}")
    public String showTask(@PathVariable("id") int id, Model model){
        model.addAttribute("task",taskDAO.show(id));
        return "tasks/show";
    }

    //create
    @GetMapping("/new-task")
    public String newTask(@ModelAttribute("task") Task task){
        return "tasks/new";
    }

    @PostMapping()
    public String createTask(@ModelAttribute("task") Task task) {
        taskDAO.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") int id, Model model) {
        model.addAttribute("task",taskDAO.show(id));
        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String updateTask(@ModelAttribute("task") Task task, @PathVariable("id") int id) {
        taskDAO.updateTask(id,task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskDAO.delete(id);
        return "redirect:/tasks";
    }

}
