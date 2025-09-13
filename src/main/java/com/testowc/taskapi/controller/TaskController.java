package com.testowc.taskapi.controller;

import com.testowc.taskapi.model.Task;
import com.testowc.taskapi.repository.TaskRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final Validator validator;

    public TaskController(TaskRepository taskRepository,  Validator validator) {
        this.taskRepository = taskRepository;
        this.validator = validator;
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
        boolean exists = taskRepository.existsByNameAndDescriptionAndCompletedAndDueDate(
                task.getName(),
                task.getDescription(),
                task.getCompleted(),
                task.getDueDate()
        );

        if (exists) { throw new RuntimeException("La tasca ja existeix"); }

        return taskRepository.save(task);
    }

    @PostMapping("/multiple")
    public ResponseEntity<Map<String, Object>> createMultiple(@RequestBody List<Task> tasks) {
        List<Task> correctTasks = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        List<Map<String, Object>> skipped = new ArrayList<>();

        for (Task task : tasks) {
            //Revisar que l'entrada sigui vàlida contra la informació del model
            Set<ConstraintViolation<Task>> violations = validator.validate(task);

            if  (!violations.isEmpty()) {
                skipped.add(Map.of(
                        "raó", "Validació fallada",
                        "tasca", task,
                        "errors", violations.stream().map(ConstraintViolation::getMessage).toList()
                ));
                continue;
            }

            //Revisar que les entrades no es repeteixin dins del cos de la request
            String key = task.getName() + "|" + task.getDescription() + "|" + task.getCompleted() + "|" + task.getDueDate();
            if (seen.contains(key)) {
                skipped.add(Map.of(
                        "raó", "Duplicat a la càrrega",
                        "tasca", task
                ));
                continue;
            }

            seen.add(key);

            //Revisar que les entrades no existeixen dins la BDD
            boolean exists = taskRepository.existsByNameAndDescriptionAndCompletedAndDueDate(
                    task.getName(),
                    task.getDescription(),
                    task.getCompleted(),
                    task.getDueDate()
            );

            if (exists) {
                skipped.add(Map.of(
                        "raó", "Ja existeix a la BDD",
                        "tasca", task
                ));
            } else {
                correctTasks.add(task);
            }
        }

        List<Task> saved = taskRepository.saveAll(correctTasks);

        //Formulació de la resposta
        Map<String, Object> response = new HashMap<>();

        response.put("Tasques guardades", saved);
        response.put("Tasques omeses", skipped);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id, @Valid @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("La tasca no existeix"));

        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.getCompleted());
        task.setDueDate(taskDetails.getDueDate());

        return taskRepository.save(task);
    }

    @PutMapping("/complete/{id}")
    public Task complete(@PathVariable String id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("La tasca no existeix"));

        task.setCompleted(taskDetails.getCompleted());

        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        taskRepository.deleteById(id);
    }
}
