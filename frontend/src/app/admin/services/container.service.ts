import { Injectable } from '@angular/core';
import { ConfigService } from '../../services/config/config.service';
import { HttpClient } from '@angular/common/http';
import { Container } from '../modules/container';
import { catchError, map, Observable, of } from 'rxjs';
import { ApiResponses } from '../../modules/api-responses';

@Injectable({
  providedIn: 'root',
})
export class ContainerService {
  private readonly url: string;
  getUsersPaginated: any;

  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API}${this.configService.ENDPOINTS.settings}/containers`;
  }

  getAll(): Observable<Container[]> {
    const urls = `${this.url}/all`;
    return this.http
      .get<ApiResponses<Container[]>>(urls, this.configService.httpOptions)
      .pipe(
        map((response) => response.response || []),
        catchError(() => {
          return of([]); // Fallback for errors
        })
      );
  }

  show(id: string): Observable<Container | null> {
    const url = `${this.url}/${id}`;
    return this.http
      .get<ApiResponses<Container>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.response) {
            throw new Error('data not found');
          }
          return response.response;
        }),
        catchError(() => {
          return of(null); // Retourner vide en cas d'erreur
        })
      );
  }

  create(data: Container): Observable<ApiResponses<void>> {
    return this.http
      .post<ApiResponses<void>>(
        this.url + '/create',
        data,
        this.configService.httpOptions
      )
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to create data');
          }
          return response;
        }),
        catchError((error) => {
          return of({
            isSuccess: false,
          } as ApiResponses<void>);
        })
      );
  }

  update(id: string, data: Container): Observable<ApiResponses<void>> {
    const url = `${this.url}/${id}`;
    return this.http
      .put<ApiResponses<void>>(url, data, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to update data');
          }
          return response;
        }),
        catchError((error) => {
          return of({
            isSuccess: false,
          } as ApiResponses<void>);
        })
      );
  }

  delete(id: string): Observable<ApiResponses<void>> {
    const url = `${this.url}/${id}`;

    return this.http
      .delete<ApiResponses<void>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to delete data');
          }
          return response;
        }),
        catchError((error) => {
          return of({
            isSuccess: false,
          } as ApiResponses<void>);
        })
      );
  }

  // get data using pagination method for server-side processing
  getPaginatedData(dtParams: any): Observable<any> {
    const requestBody = {
      pagination: {
        pageNumber: dtParams.start / dtParams.length + 1,
        pageSize: dtParams.length,
        sortBy: dtParams.columns[dtParams.order[0].column].data,
        sortDirection: dtParams.order[0].dir.toUpperCase(),
      },
      search: dtParams.search.value ?? null, // Pass search string to backend
    };

    return this.http
      .post<any>(this.url, requestBody, this.configService.httpOptions)
      .pipe(
        map((response) => ({
          draw: dtParams.draw,
          recordsTotal: response.response.totalElementCount,
          recordsFiltered: response.response.totalElementCount,
          data: response.response.content,
        })),
        catchError(() =>
          of({
            draw: 0,
            recordsTotal: 0,
            recordsFiltered: 0,
            data: [],
          })
        )
      );
  }
}
