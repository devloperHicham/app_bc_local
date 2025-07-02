import { Injectable } from '@angular/core';
import { ConfigService } from '../../services/config/config.service';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private readonly url: string;
  getUsersPaginated: any;

  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API}${this.configService.ENDPOINTS.settings}/notifications`;
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
