package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public String createTask(@RequestBody Task task) {
        service.addTask(task);
        return "Task created successfully!";
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return service.getTaskById(id);
    }

    @PutMapping("/{id}/complete")
    public String markTaskCompleted(@PathVariable int id) {
        service.markTaskCompleted(id);
        return "Task marked as completed!";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        return "Task deleted successfully!";
    }

    @GetMapping("/highest-priority")
    public Task getHighestPriorityTask() {
        return service.getHighestPriorityTask();
    }

    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return service.getStats();
    }
}
