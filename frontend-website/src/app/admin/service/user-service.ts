import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from '../../services/config/config';
import { Inscription } from '../inscription/inscription';
import { catchError, map, Observable, of } from 'rxjs';
import { ApiResponses } from '../../modules/api-responses';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly url: string;
  private readonly url_auth: string;
  getUsersPaginated: any;

  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API_AUTH}${this.configService.ENDPOINTS.users}`;
    this.url_auth = `${this.configService.URL_API_AUTH}${this.configService.ENDPOINTS.users}`;
  }

  show(id: string): Observable<Inscription | null> {
    const url = `${this.url}/${id}`;
    return this.http
      .get<ApiResponses<Inscription>>(url, this.configService.httpOptions)
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

  create(data: Inscription): Observable<ApiResponses<void>> {
    return this.http
      .post<ApiResponses<void>>(
        this.url_auth + '/register',
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

  update(id: string, data: Inscription): Observable<ApiResponses<void>> {
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

  resetPassword(
    id: string,
    data: { newPassword: string }
  ): Observable<ApiResponses<void>> {
    const url = `${this.url}/reset-password/${id}`;
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
}
