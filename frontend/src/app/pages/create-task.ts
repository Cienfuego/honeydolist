import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TaskService } from '../services/task';
import { UserService } from '../services/user';
import { AuthService } from '../services/auth';
import { CreateTaskDto } from '../models/create-task';
import { User } from '../models/user';

@Component({
  selector: 'app-create-task',
  standalone: true,
  templateUrl: './create-task.html',
  styleUrls: ['./create-task.scss'],
  imports: [CommonModule, FormsModule]
})
export class CreateTaskComponent implements OnInit {
  newTask: Partial<CreateTaskDto> = {
    title: '',
    description: '',
    dueDate: '',
    priority: 'MEDIUM',
    assigneeId:  0,
    assignorId:  0 
  };

  users: User[] = [];

  constructor(
    private taskService: TaskService,
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService.getActiveUsers().subscribe(users => this.users = users);
    const userId = this.authService.getUserId();
    if (userId) {
      this.newTask.assignorId = userId;
      this.newTask.assigneeId = userId;
    }
  }

  onSubmit(): void {
    this.taskService.createTask(this.newTask as CreateTaskDto).subscribe({
      next: () => this.router.navigate(['/hello']),
      error: err => {
        console.error('Task creation failed', err);
        alert('Error creating task.');
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/hello']);
  }
}

