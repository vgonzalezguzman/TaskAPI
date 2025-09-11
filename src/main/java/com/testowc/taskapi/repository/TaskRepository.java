package com.testowc.taskapi.repository;

import com.testowc.taskapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
