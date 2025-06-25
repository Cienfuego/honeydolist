package com.example.backend.controller;


import com.example.backend.model.Comment;
import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;


    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody String newText) {
        return commentService.updateComment(commentId, newText)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId)
                ? ResponseEntity.ok("Comment deleted successfully")
                : ResponseEntity.notFound().build();
    }



// Add a new comment to a task
    @PostMapping("/task/{taskId}/user/{userId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody Comment comment
    ) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        Optional<User> userOpt = userRepository.findById(userId);
        String user = userOpt.get().getUsername();

        if (taskOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid task or user ID.");
        }

        comment.setTask(taskOpt.get());
        comment.setAuthor(user);
        comment.setCreatedAt(java.time.LocalDateTime.now());

        return ResponseEntity.ok(commentRepository.save(comment));
    }
//
//    // ✅ 2. Get all comments for a task (ordered by creation time)
    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getCommentsForTask(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Comment> comments = commentRepository.findByTask(taskOpt.get());
        return ResponseEntity.ok(comments);
    }
}
