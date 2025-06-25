export interface Comment {
  id?: number;
  text: string;
  createdAt?: string;
  user: { id: number };
  task: { id: number };
}