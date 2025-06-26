package com.example.backend.controller;



import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRepositoryExtended;
import com.example.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;

@Component
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRepositoryExtended userRepositoryExtended;

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        return taskService.createTask(task)
                .<ResponseEntity<?>>map(savedTask -> ResponseEntity.ok(savedTask))
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid assignor or assignee ID"));
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }


    @GetMapping("/active")
    public ResponseEntity<?> getActiveTasks() {
        return ResponseEntity.ok(taskService.getActiveTasks());
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }

    @GetMapping("/completed/{userId}")
    public ResponseEntity<?> getCompletedTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getCompletedTasks(userId));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<?> getTasksAssignedToUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksAssignedToUser(userId));
    }

    @GetMapping("/created/{userId}")
    public ResponseEntity<?> getTasksCreatedByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksCreatedByUser(userId));
    }

    @GetMapping("/assigned/{userId}/priority/{priority}")
    public ResponseEntity<?> getTasksByPriority(@PathVariable Long userId, @PathVariable String priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(userId, priority));
    }

    @GetMapping("/assigned/{userId}/due-soon")
    public ResponseEntity<?> getTasksDueSoon(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "3") int daysAhead) {
        return ResponseEntity.ok(taskService.getTasksDueSoon(userId, daysAhead));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        return taskService.updateTask(taskId, updatedTask)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long taskId) {
        Long blah = taskId;
        Optional<Task> updatedTask = taskService.completeTask(taskId);
        return updatedTask
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{taskId}/active")
    public ResponseEntity<?> markTaskActive(@PathVariable Long taskId) {
        Long blah = taskId;
        Optional<Task> updatedTask = taskService.markTaskActive(taskId);
        return updatedTask
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId)
                ? ResponseEntity.ok("Task deleted successfully")
                : ResponseEntity.notFound().build();
    }

//        Optional<User> assignor = userRepository.findById(task.getAssignor().getId());
//        Optional<User> assignee = userRepository.findById(task.getAssignee().getId());
//
//        if (assignor.isEmpty() || assignee.isEmpty()) {
//            return ResponseEntity.badRequest().body("Assignor or Assignee not found.");
//        }
//
//        task.setAssignor(assignor.get());
//        task.setAssignee(assignee.get());
//        task.setCreatedAt(java.time.LocalDateTime.now());
//
//        return ResponseEntity.ok(taskRepository.save(task));
//    }

//     2. Get all active tasks (non-completed, not overdue)
//    @GetMapping("/active")
//    public List<Task> getActiveTasks() {
//        return taskRepository.findByCompletedFalseOrderByCreatedAtDesc();
//    }
//
//
//
//    //  3. Get overdue tasks
//    @GetMapping("/overdue")
//    public List<Task> getOverdueTasks() {
//        return taskRepository.findByDueDateBeforeAndCompletedFalse(LocalDate.now());
//    }
////
////    //  4. Get completed tasks by user ID
//    @GetMapping("/completed/{userId}")
//    public ResponseEntity<?> getCompletedTasks(@PathVariable Long userId) {
//        Optional<User> user = userRepository.findById(userId);
//        return user.map(value -> ResponseEntity.ok(taskRepository.findByCompletedTrueAndAssignee(value)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
////
////    // 5. Mark a task as complete
//    @PutMapping("/{taskId}/complete")
//    public ResponseEntity<?> markTaskComplete(@PathVariable Long taskId) {
//        Optional<Task> task = taskRepository.findById(taskId);
//        if (task.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Task t = task.get();
//        t.setCompleted(true);
//        taskRepository.save(t);
//        return ResponseEntity.ok("Task marked as complete");
//    }
}

