export interface DataTablesResponse<T> {
  draw: number;
  recordsTotal: number;
  recordsFiltered: number;
  data: T[];
}
export interface DataTablesError {
  error: string;
  message: string;
  status: number;
}
