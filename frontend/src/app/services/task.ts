import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Task } from '../models/task';
import { Observable } from 'rxjs';
import { CreateTaskDto } from '../models/create-task'



@Injectable({ providedIn: 'root' })
export class TaskService {
  private baseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getAllActiveTasks(): Observable<Task[]> {
  return this.http.get<Task[]>(`${this.baseUrl}/active`);
}
  
  getTasksAssignedTo(userId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/assigned/${userId}`);
  }

  getTasksCreatedBy(userId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/created/${userId}`);
  }

  getTaskById(taskId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/${taskId}`);
  }

  createTask(task: CreateTaskDto): Observable<Task> {
  return this.http.post<Task>(`${this.baseUrl}/create`, task);
}

  updateTask(taskId: number, task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${taskId}`, task);
  }

  deleteTask(taskId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${taskId}`);
  }

  markComplete(taskId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${taskId}/complete`, {});
  }

  

}

