import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Promise<void> {
    const body = { username, password };
    localStorage.setItem('username', username);
    return firstValueFrom(this.http.post<void>(this.apiUrl, body));
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  editUser(oldUsername: string, newUsername: string, newPassword: string) {
    const body = { oldUsername, newUsername, newPassword };
    console.log("made it to auth.ts")
    return this.http.post('http://localhost:8080/api/auth/edituser', body);
  }

  deleteUser(username: string) {
    return this.http.delete(`http://localhost:8080/api/auth/${username}`);
  }


}
