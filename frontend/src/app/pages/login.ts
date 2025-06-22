import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth';

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

  async onLogin(): Promise<void> {
    this.errorMessage = '';

    try {
      await this.authService.login(this.username, this.password);
      this.router.navigateByUrl('/hello');
    } catch (error) {
      this.errorMessage = 'Invalid username or password.';
    }
  }

  onRegister(): void {
    this.router.navigate(['/register']);
  }
}

