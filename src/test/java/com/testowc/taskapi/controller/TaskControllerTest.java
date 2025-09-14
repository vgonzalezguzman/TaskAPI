package com.testowc.taskapi.controller;

import com.testowc.taskapi.model.Task;
import com.testowc.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.data.mongodb.uri=mongodb://localhost:27017/toDoApp_test")
public class TaskControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void getAllTasks_returnsSavedTasks() {
        Task t1 = new Task("Test task 1", "Test description 1", false, new Date());
        Task t2 = new Task("Test task 2", "Test description 2", false, new Date());
        taskRepository.saveAll(List.of(t1,t2));

        ResponseEntity<Task[]> response = restTemplate.getForEntity("/api/tasks", Task[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task[] body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(2);

        List<String> names = Arrays.stream(body)
                .map(Task::getName)
                .collect(Collectors.toList());
        assertThat(names).containsExactlyInAnyOrder("Test task 1", "Test task 2");
    }

    @Test
    void getTaskById_returnsTask() {
        Task savedTask = taskRepository.save(new Task("Test task 1", "Test description 1", false, new Date()));

        ResponseEntity<Task> response = restTemplate.getForEntity("/api/tasks/" + savedTask.getId(), Task.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo("Test task 1");
    }

    @Test
    void uploadTask_modifyTask() {
        Date futureDate = new Date((System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        Task savedTask = taskRepository.save(new Task("Test task 1", "Test description 1", false, new Date()));
        Task updatedTask = new Task("Test task updated", "Test description updated", true, futureDate);

        HttpEntity<Task> request = new HttpEntity<>(updatedTask);
        ResponseEntity<Task> response = restTemplate.exchange(
                "/api/tasks/" + savedTask.getId(),
                HttpMethod.PUT,
                request,
                Task.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("Test task updated");
        assertThat(responseBody.getDescription()).isEqualTo("Test description updated");
        assertThat(responseBody.getCompleted()).isEqualTo(true);

        Task taskFromDb = taskRepository.findById(savedTask.getId()).orElseThrow();
        assertThat(taskFromDb.getName()).isEqualTo("Test task updated");
    }

    @Test
    void uploadBadData_returnsBadRequest() {
        Date date = new Date((System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        Task task = new Task("", "Test for bad request", false, date);

        HttpEntity<Task> request = new HttpEntity<>(task);
        ResponseEntity<Task> response = restTemplate.exchange(
                "/api/tasks",
                HttpMethod.POST,
                request,
                Task.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteTaskById_returnsOK() {
        Task savedTask = taskRepository.save(new Task("Test task 1", "Test description 1", false, new Date()));
        ResponseEntity<Task> response = restTemplate.exchange(
                "/api/tasks/" + savedTask.getId(),
                HttpMethod.DELETE,
                null,
                Task.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
