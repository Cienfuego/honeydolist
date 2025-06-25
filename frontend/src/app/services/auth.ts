import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {tap} from 'rxjs'


export interface LoginResponse {
  id: number;
  username: string;
  message: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}



  
  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(response => {
        localStorage.setItem('userId', response.id.toString());
        localStorage.setItem('username', response.username);
      })
    );
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  getUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }


  editUser(oldUsername: string, newUsername: string, newPassword: string) {
    const body = { oldUsername, newUsername, newPassword };
    console.log("made it to auth.ts")
    return this.http.post('http://localhost:8080/api/auth/edituser', body);
  }

  deleteUser(username: string) {
    return this.http.delete(`${this.apiUrl}/${username}`);
  }

  updateUser(userId: number, username: string, password: string) {
  return this.http.put<number>(`${this.apiUrl}/users/${userId}`, { username, password }).pipe(
    tap(newUserId => {
      localStorage.setItem('userId', newUserId.toString());
      localStorage.setItem('username', username);
    })
  );
}



}