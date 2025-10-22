package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(Task task) {
        repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(int id) {
        return repository.findById(id);
    }

    public void markTaskCompleted(int id) {
        repository.markCompleted(id);
    }

    public void deleteTask(int id) {
        repository.deleteById(id);
    }

    public Task getHighestPriorityTask() {
        return repository.findHighestPriority();
    }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", repository.getTotalTasks());
        stats.put("completed", repository.getCompletedTasks());
        stats.put("pending", repository.getPendingTasks());
        return stats;
    }
}
