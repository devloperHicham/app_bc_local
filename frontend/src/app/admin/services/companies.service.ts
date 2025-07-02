import { Injectable } from '@angular/core';
import { ConfigService } from '../../services/config/config.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Company } from '../modules/company';
import { catchError, map, Observable, of} from 'rxjs';
import { ApiResponses } from '../../modules/api-responses';

@Injectable({
  providedIn: 'root',
})
export class CompaniesService {
  private readonly url: string;
  getUsersPaginated: any;

  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API}${this.configService.ENDPOINTS.settings}/companies`;
  }

  getAll(): Observable<Company[]> {
    const urls = `${this.url}/all`;
    return this.http
      .get<ApiResponses<Company[]>>(urls, this.configService.httpOptions)
      .pipe(
        map((response) => response.response || []),
        catchError(() => {
          return of([]); // Fallback for errors
        })
      );
  }

  show(id: string): Observable<Company | null> {
    const url = `${this.url}/${id}`;
    return this.http
      .get<ApiResponses<Company>>(url, this.configService.httpOptions)
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

  /**
   * Sends a POST request to create a new company record.
   *
   * @param data - The FormData object containing the company details to be created.
   * @returns An Observable emitting the API response indicating success or failure.
   * @throws An error if the creation request is unsuccessful.
   */

  create(data: FormData): Observable<ApiResponses<void>> {
    return this.http
      .post<ApiResponses<void>>(
        this.url + '/create',
        data,
        this.configService.httpOptionOnlyTokens
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

  update(id: string, data: FormData): Observable<ApiResponses<void>> {
    const url = `${this.url}/${id}`;
    return this.http
      .put<ApiResponses<void>>(url, data, this.configService.httpOptionOnlyTokens)
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
          console.log('ID delete:', id);
          console.log('Response from delete:', response);
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
  /**
   * Gets company logo image from backend
   * @param filename The name of the logo file to retrieve
   * @returns Observable of Blob containing the image data
   */
  getCompanyLogo(filename: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('access_token')}`,
    });

    return this.http
      .get(`${this.url}/images/${filename}`, { headers, responseType: 'blob' })
      .pipe(
        map((blob) => URL.createObjectURL(blob)),
        catchError(() => {
          console.error('Error downloading Image...');
          return of('');
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
      search: dtParams.search.value ?? null, // 👈 Pass search string to backend
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
