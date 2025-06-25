import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-profile.html',
  styleUrls: ['./edit-profile.scss']
})
export class EditProfileComponent {
  username = '';
  password = '';
  errorMessage = '';
  successMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.username = this.authService.getUsername() || '';
  }

  onSubmit() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.errorMessage = 'User ID not found.';
      return;
    }

    this.authService.updateUser(userId, this.username, this.password).subscribe({
      next: () => {
        this.successMessage = 'Profile updated successfully!';
        setTimeout(() => this.router.navigate(['/hello']), 1500);
      },
      error: err => {
        this.errorMessage = err.error || 'Update failed';
      }
    });
  }

  onCancel() {
    this.router.navigate(['/hello']);
  }
}


