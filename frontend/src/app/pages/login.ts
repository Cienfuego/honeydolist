import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, LoginResponse } from '../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  id = '';
  errorMessage = '';

  // constructor(private router: Router) {}

  // onLogin(): void {
  //   // Dummy login logic
  //   if (this.username && this.password) {
  //     this.router.navigateByUrl('/hello');
  //   } else {
  //     this.errorMessage = 'Please enter both username and password.';
  //   }
  // }

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response: LoginResponse) => {
        console.log('Login successful, user ID:', response.id);
        this.router.navigate(['/hello']);
      },
      error: err => {
        this.errorMessage = 'Login failed. Please try again.';
      }
    });
  }

  onRegister(): void {
    this.router.navigate(['/register']);
  }
}

