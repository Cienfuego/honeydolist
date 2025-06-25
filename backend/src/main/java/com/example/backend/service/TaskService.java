package com.example.backend.service;


import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRepositoryExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryExtended userRepositoryExtended;


    public Optional<Task> createTask(Task task) {
//       Optional<User> assignor = userRepository.findById(task.getAssignor().getId());
//       Optional<User> assignee = userRepository.findById(task.getAssignee().getId());

//       Optional<Long> assignorId = userRepository.findById(task.getAssignorId());
//       Optional<Long> assigneeId = userRepository.findById(task.getAssigneeId());

//       Optional<Long> assignorId = userRepositoryExtended.findById(task.getAssignorId());
//       Optional<Long> assigneeId = userRepositoryExtended.findById(task.getAssigneeId());

//        if (assignorId.isEmpty() || assigneeId.isEmpty()) {
//            return Optional.empty();
//        }
//
//        task.setAssignorId(assignorId.get());
//        task.setAssigneeId(assigneeId.get());
//        task.setCreatedAt(LocalDateTime.now());
        return Optional.of(taskRepository.save(task));
    }

    public List<Task> getActiveTasks() {
        return taskRepository.findByCompletedFalseOrderByCreatedAtDesc();
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndCompletedFalse(LocalDate.now());
    }

    public List<Task> getCompletedTasks(Long userId) {
        return userRepository.findById(userId)
                .map(taskRepository::findByCompletedTrueAndAssigneeId)
                .orElse(List.of());
    }

    public boolean completeTask(Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) return false;

        Task task = taskOpt.get();
        task.setCompleted(true);
        taskRepository.save(task);
        return true;
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return taskRepository.findById(taskId).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());

            // Reassign assignee if needed
//            if (updatedTask.getAssigneeId() != null) {
//                userRepositoryExtended.findById(updatedTask.getAssigneeId()).ifPresent(existingTask::setAssigneeId);
//            }

            return taskRepository.save(existingTask);
        });
    }

    public boolean deleteTask(Long taskId) {
        return taskRepository.findById(taskId).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }

//    public List<Task> getTasksAssignedToUser(Long userId) {
//        return userRepository.findById(userId)
////                .map(user -> taskRepository.findByAssigneeIdAndCompletedFalseOrderByCreatedAtDesc(user))
//                .map(user -> taskRepository.findByAssignee_Id(user))
//                .orElse(List.of());
//    }

    public List<Task> getTasksAssignedToUser(Long userId) {
        return taskRepository.findByAssigneeId(userId);
    }

    public List<Task> getTasksCreatedByUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> taskRepository.findByAssignorIdAndCompletedFalseOrderByCreatedAtDesc(user))
                .orElse(List.of());
    }

    public List<Task> getTasksByPriority(Long userId, String priority) {
        return userRepository.findById(userId)
                .map(user -> taskRepository.findByAssigneeIdAndPriorityOrderByCreatedAtDesc(user, priority))
                .orElse(List.of());
    }

    public List<Task> getTasksDueSoon(Long userId, int daysAhead) {
        LocalDate cutoff = LocalDate.now().plusDays(daysAhead);
        return userRepository.findById(userId)
                .map(user -> taskRepository.findByAssigneeIdAndDueDateBeforeAndCompletedFalseOrderByDueDateAsc(user, cutoff))
                .orElse(List.of());
    }

}

