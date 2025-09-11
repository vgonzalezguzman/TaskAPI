package com.testowc.taskapi.repository;

import com.testowc.taskapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByCompleted(Boolean completed);
    boolean existsByNameAndDescriptionAndCompletedAndDueDate(String name, String description, boolean completed, Date dueDate);
}
