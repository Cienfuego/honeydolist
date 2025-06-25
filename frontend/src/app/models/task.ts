export interface Task {
  id: number;
  title: string;
  description: string;
  dueDate: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  assignorId: number;
  assigneeId: number;
  completed?: boolean;
  createdAt?: string;
  comments: [];
}
