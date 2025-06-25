import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../services/task';
import { CommentService } from '../services/comment';
import { AuthService } from '../services/auth';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-task-detail',
  standalone: true,
  templateUrl: './task-detail.html',
  imports: [CommonModule, FormsModule],
  styleUrls: ['./task-detail.scss'],
})
export class TaskDetailComponent implements OnInit {
  taskId!: number;
  task: any;
  comments: any[] = [];
  newComment: string = '';
  userId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private commentService: CommentService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    console.log("made it to taskdetail.ts")
    
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    this.userId = this.authService.getUserId();
    console.log("task id is: " + this.taskId)
    this.taskService.getTaskById(this.taskId).subscribe(task => this.task = task);
    this.commentService.getCommentsByTaskId(this.taskId).subscribe(comments => this.comments = comments);
  }

  submitComment() {
    if (this.newComment.trim()) {
      this.commentService.addComment(this.taskId, this.userId!, this.newComment).subscribe(() => {
        this.comments.push({ text: this.newComment, createdAt: new Date(), user: { id: this.userId } });
        this.newComment = '';
      });
    }
  }

  completeTask() {
    this.taskService.markComplete(this.taskId).subscribe(() => {
      this.task.completed = true;
    });
  }
}
