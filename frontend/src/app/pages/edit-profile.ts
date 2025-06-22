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
  newUsername = '';
  newPassword = '';
  errorMessage = '';
  successMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    const oldUsername = this.authService.getUsername();
    console.log("made it here")
    if (!oldUsername) return;

    this.authService.editUser(oldUsername, this.newUsername, this.newPassword).subscribe({
      
      next: () => {
        this.successMessage = 'Profile updated!';
        //this.authService.logout();
        localStorage.setItem('username', this.newUsername);
        console.log("made it to next")
        setTimeout(() => this.router.navigate(['/hello']), 1500);
      },
      error: err => {
        this.errorMessage = err.error?.message || 'Update failed';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/hello']);
  }
}

