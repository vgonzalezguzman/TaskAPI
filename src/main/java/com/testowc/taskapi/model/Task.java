package com.testowc.taskapi.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    @NotBlank(message = "El camp nom no pot quedar en blanc")
    private String name;

    private String description;

    private Boolean completed;

    @FutureOrPresent(message = "La data no pot quedar al passat")
    private Date dueDate;

    @CreatedDate
    private Date createdDate;

    public Task() {}
    public Task(String name, String description, Boolean completed, Date dueDate) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
