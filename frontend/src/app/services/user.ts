import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({providedIn: 'root'})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getActiveUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/active`);
  }

  getUserById(userId: number): Observable<any>{
      console.log("userid in service: " + userId);
    return this.http.get(`${this.apiUrl}/${userId}/username`);
  }
}
