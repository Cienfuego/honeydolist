import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comment {
  id?: number;
  text: string;
  createdAt?: string;
  user?: {
    id: number;
    username?: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) {}

  getCommentsByTaskId(taskId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/task/${taskId}`);
  }

  addComment(taskId: number, userId: number, text: string): Observable<Comment> {
    return this.http.post<Comment>(`${this.apiUrl}/task/${taskId}/user/${userId}`, { text });
  }

  editComment(commentId: number, text: string): Observable<Comment> {
    return this.http.put<Comment>(`${this.apiUrl}/${commentId}`, { text });
  }

  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${commentId}`);
  }
}
