export interface PaginationRequest {
  pagination: {
    pageNumber: number;
    pageSize: number;
    sortBy?: string;
    sortDirection?: 'ASC' | 'DESC';
  };
  filters?: {
    [key: string]: string;
  };
}