import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from '../../services/config/config.service';
import { ApiResponses } from '../../modules/api-responses';
import { EditPassword } from '../modules/edit-password';
import { catchError, map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EditPasswordService {
  private readonly url: string;
  getUsersPaginated: any;

  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API}${this.configService.ENDPOINTS.users}`;
  }
  update(id: string, data: EditPassword): Observable<ApiResponses<void>> {
    const url = `${this.url}/password/${id}`;
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
