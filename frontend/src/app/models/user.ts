export interface User {
  id: number;
  username: string;
  password: string;
  dateCreated: string;
  dateDeleted?: string | null;
}