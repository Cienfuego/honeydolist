import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-hello',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hello.html',
  styleUrls: ['./hello.scss']
  // template: `
  //   <div class="hello-container">
  //     <h2>Hello, World!</h2>
  //     <p>You are now logged in.</p>
  //   </div>
  // `

})
export class HelloComponent {
  constructor(private router: Router, private authService: AuthService) {}

  onLogout(): void {
    //this.authService.logout();
    this.router.navigate(['/login']);
  }


  username: string | null = null;
  showModal = false;
  ngOnInit() {
    this.username = this.authService.getUsername();
}

onEdit(): void {
    this.router.navigate(['/edit-profile']);
  }


onDelete(): void {
    // You can add a confirmation dialog here before deleting
    //if (confirm('Are you sure you want to delete your account?')) {
      const username = this.authService.getUsername();
      console.log(username);
      if (username) {
        console.log("made it here");
        this.authService.deleteUser(username).subscribe({
          next: () => {
            //this.authService.logout();
            //this.router.navigate(['/login']);
          },
          error: err => {
            console.error('Delete failed', err);
            alert('An error occurred while deleting your account.');
          }
        });
         this.router.navigate(['/login'])
      }
    //}
  }

   confirmDelete() {
    this.showModal = false;
    this.onDelete(); // your existing delete logic
  }
}
