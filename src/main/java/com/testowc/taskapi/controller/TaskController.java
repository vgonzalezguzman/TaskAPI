package com.testowc.taskapi.controller;

import com.testowc.taskapi.model.Task;
import com.testowc.taskapi.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> all() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @GetMapping("/filter")
    public List<Task> getTasksByCompleted(@RequestParam Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    @PostMapping
    public Task create(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        taskRepository.deleteById(id);
    }
}
