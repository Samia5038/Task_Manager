package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Task> taskRowMapper = (rs, rowNum) ->
            new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("priority"),
                    rs.getBoolean("completed")
            );

    // Create task
    public int save(Task task) {
        String sql = "INSERT INTO tasks (title, description, priority, completed) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getPriority(), task.isCompleted());
    }

    // Get all tasks
    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, taskRowMapper);
    }

    // Get task by ID
    public Task findById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, id);
    }

    // Mark task as completed
    public int markCompleted(int id) {
        String sql = "UPDATE tasks SET completed = TRUE WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Delete task
    public int deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Get highest priority task
    public Task findHighestPriority() {
        String sql = "SELECT * FROM tasks ORDER BY priority DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, taskRowMapper);
    }

    // Task stats
    public int getTotalTasks() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tasks", Integer.class);
    }

    public int getCompletedTasks() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tasks WHERE completed = TRUE", Integer.class);
    }

    public int getPendingTasks() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tasks WHERE completed = FALSE", Integer.class);
    }
}
