export interface CreateTaskDto {
  title: string;
  description: string;
  dueDate: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  assignorId: number;
  assigneeId: number;
  //assigneeId: number;
}
