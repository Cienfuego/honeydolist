package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate dueDate;
    @Column(length = 10)
    private String priority; // e.g., LOW, MEDIUM, HIGH
    private boolean completed = false;
    private LocalDateTime createdAt = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name = "assignor_id", nullable = true)
//    private User assignor;
//
//    @ManyToOne
//    @JoinColumn(name = "assignee_id", nullable = true)
//    private User assignee;

    @Column(name = "assignor_id")
    private Long assignorId;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // --- Getters and Setters ---

    public Long getAssignorId(){return assignorId;}
    public Long getAssigneeId(){return assigneeId;}

    public void setAssignorId(Long assignorId){this.assignorId=assignorId;}
    public void setAssigneeId(Long assigneeId){this.assigneeId=assigneeId;}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
