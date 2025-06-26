package com.example.backend.repository;

import com.example.backend.model.Task;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedFalseOrderByCreatedAtDesc();

    List<Task> findByCompletedTrueOrderByCreatedAtDesc();
    List<Task> findByCompletedFalseAndAssigneeId(User assignee);
    List<Task> findByDueDateBeforeAndCompletedFalse(LocalDate date);
    List<Task> findByCompletedTrueAndAssigneeId(User assignee);

    // Filter tasks assigned to a user
    List<Task> findByAssigneeIdAndCompletedFalseOrderByCreatedAtDesc(User assignee);

    // Completed tasks for a user
    List<Task> findByAssigneeIdAndCompletedTrueOrderByCreatedAtDesc(User assignee);

    // Tasks by priority
    List<Task> findByAssigneeIdAndPriorityOrderByCreatedAtDesc(User assignee, String priority);

    // Tasks due soon
    List<Task> findByAssigneeIdAndDueDateBeforeAndCompletedFalseOrderByDueDateAsc(User assignee, LocalDate date);

    // Tasks created by the user
    List<Task> findByAssignorIdAndCompletedFalseOrderByCreatedAtDesc(User assignor);

    List<Task> findByAssigneeId(Long assigneeId);
}
