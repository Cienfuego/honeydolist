export interface User {
  id: number;
  username: string;
  dateCreated: string;
  dateDeleted?: string | null;
}