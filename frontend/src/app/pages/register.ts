import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.scss']
})
export class RegisterComponent {
  username = '';
  password = '';
  errorMessage = '';
  successMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit(): void {
    this.errorMessage = '';
    this.successMessage = '';

    const user = {
      username: this.username,
      password: this.password
    };

    this.http.post('http://localhost:8080/api/auth/register', user).subscribe({
      next: () => {
        this.successMessage = 'Registration successful!';
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: err => {
        if (err.status === 409) {
          this.errorMessage = 'Username already exists.';
        } else {
          this.errorMessage = 'Registration failed.';
        }
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/login']);
  }
}

