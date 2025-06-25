//package com.example.backend.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "comments")
//@Data // includes @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
//public class Comment_bak {
//
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String text;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private User author;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id", nullable = false)
//    @JsonBackReference
//    private Task task;
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime dt) {
//        this.createdAt = dt;
//    }
//
//    public User getAuthor(){return author;}
//
//    public void setAuthor(User author){
//        this.author = author;
//    }
//
//    public Task getTask(){return task;}
//
//    public void setTask(Task task){
//        this.task = task;
//    }
//
//    public void setText(String text) {this.text = text;}
//
//}
