export interface ApiResponses<T> {
  time: string;
  httpStatus: string;
  isSuccess: boolean;
  response?: T; // <- match the backend key
}