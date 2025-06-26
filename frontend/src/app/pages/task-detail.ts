import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../services/task';
import { CommentService } from '../services/comment';
import { AuthService } from '../services/auth';
import { UserService } from '../services/user'
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { create } from 'domain';


@Component({
  selector: 'app-task-detail',
  standalone: true,
  templateUrl: './task-detail.html',
  imports: [CommonModule, FormsModule],
  styleUrls: ['./task-detail.scss'],
})
export class TaskDetailComponent implements OnInit {
  taskId!: number;
  task: any | null = null
  
  comments: any[] = [];
  newComment: string = '';
  userId: number | null = null;

  assignee_name: string | null = null;
  assignor_name: string = '';

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private commentService: CommentService,
    private authService: AuthService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    this.userId = this.authService.getUserId();
    this.taskService.getTaskById(this.taskId).subscribe(task => {
      this.task = task; 
      if (task.assignorId) {
        this.userService.getUserById(task.assignorId).subscribe(user => {
          this.assignor_name = user.username;});       
        } 
      if (task.assigneeId) {
        this.userService.getUserById(task.assigneeId).subscribe(user => {
          this.assignee_name = user.username;});
        } 
    });
    
    this.commentService.getCommentsByTaskId(this.taskId).subscribe(comments => this.comments = comments);
    
  }

  submitComment() {
    if (this.newComment.trim()) {
      // this.commentService.addComment(this.taskId, this.userId!, this.newComment).subscribe(() => {
      //   this.comments.push({ text: this.newComment, createdAt: new Date(), user: { id: this.userId } });
      //   this.newComment = '';
      
      // });
      this.commentService.addComment(this.taskId, this.userId!, this.newComment).subscribe((createdComment) => {
        this.comments.push(createdComment);
        //console.log(createdComment);
        this.newComment = '';
      
      });
    }
  }

  toggleTaskCompletion(): void {
  if (!this.task) return;

  const taskId = this.task.id;

  const toggleObservable = this.task.completed
    ? this.taskService.markActive(taskId)
    : this.taskService.markComplete(taskId);

  toggleObservable.subscribe({
    next: (updatedTask) => {
      this.task!.completed = updatedTask.completed;
    },
    error: err => {
      console.error('Error toggling task completion:', err);
      alert('An error occurred while updating task status.');
    }
  });
}


  goBack(): void {
    this.router.navigate(['/hello']);
  }
}
