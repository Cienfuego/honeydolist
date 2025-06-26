import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth';
import { TaskService } from '../services/task';
import { UserService } from '../services/user'; 
import { Task } from '../models/task';
import { User } from '../models/user';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-hello',
  imports: [CommonModule, FormsModule],
  templateUrl: './hello.html',
  styleUrls: ['./hello.scss']
})
export class HelloComponent implements OnInit {
  username: string | null = null;
  userId: number | null = null;
  showAllTasks = false;
  showDeleteModal = false;
  showCreateTaskModal = false;
  showCompletedTasks = false;

  tasks: Task[] = [];
  users: User[] = [];

  newTask = {
    
    title: '',
    description: '',
    assigneeId: null as number | null,
    dueDate: '',
    priority: 'MEDIUM'
  };

  constructor(
    private router: Router,
    private authService: AuthService,
    private taskService: TaskService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.username = this.authService.getUsername();
    this.userId = this.authService.getUserId();

    if (this.userId !== null) {
      this.loadTasks();
      this.loadUsers();
      this.loadTasks();
    }
  }
   toggleTaskView(): void {
    this.showAllTasks = !this.showAllTasks;
    this.loadTasks();
  }

  toggleCompleteTasks(): void {
    
    !this.showCompletedTasks ? this.showCompletedTasks = true 
        : this.showCompletedTasks = false
    console.log("show completed tasks from toggle complete? ", this.showCompletedTasks)
    if (this.showCompletedTasks){
        this.taskService.getCompletedTasks().subscribe({
        next: tasks => this.tasks = tasks,
        error: err => console.error('Error loading tasks:', err)
        });
    } else {
      this.loadTasks();
    }
  }

  loadTasks(): void {
    if (this.showAllTasks){
      this.taskService.getAllActiveTasks().subscribe(tasks => this.tasks = tasks);
    } else {
      this.taskService.getTasksAssignedTo(this.userId!).subscribe({
      next: tasks => this.tasks = tasks,
      error: err => console.error('Error loading tasks:', err)
      });
    }
  }

  loadUsers(): void {
    this.userService.getActiveUsers().subscribe({
      next: users => this.users = users,
      error: err => console.error('Error loading users:', err)
    });
  }

  navigateToCreateTask(): void {
  this.router.navigate(['/create-task']);
}
  
  onLogout(): void {
    this.router.navigate(['/login']);
  }

  onEdit(): void {
    this.router.navigate(['/edit-profile']);
  }

  onDelete(): void {
    const username = this.authService.getUsername();
    if (username) {
      this.authService.deleteUser(username).subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },
        error: err => {
          console.error('Delete failed', err);
          alert('An error occurred while deleting your account.');
        }
      });
    }
  }

  confirmDelete(): void {
    this.showDeleteModal = false;
    this.onDelete();
  }

  submitTask(): void {
    if (!this.newTask.assigneeId) {
      alert('Please select an assignee.');
      return;
    }
  }

  openTask(taskId: number): void {
  this.router.navigate(['/task', taskId]);
}

  cancelCreateTask(): void {
    this.showCreateTaskModal = false;
    this.resetNewTask();
  }

  resetNewTask(): void {
    this.newTask = {
      title: '',
      description: '',
      assigneeId: null,
      dueDate: '',
      priority: 'MEDIUM'
    };
  }
}
