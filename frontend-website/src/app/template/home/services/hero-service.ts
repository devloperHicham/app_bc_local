import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import {
  Company,
  Container,
  Port,
  Transportation,
} from '../../../modules/data-json';
import { ConfigService } from '../../../services/config/config';
import { ApiResponses } from '../../../modules/api-responses';

@Injectable({
  providedIn: 'root',
})
export class HeroService {
  private readonly jsonUrlPorts = 'assets/json/db_ports.json';
  private readonly jsonUrlContainers = 'assets/json/db_containers.json';
  private readonly jsonUrlCompanies = 'assets/json/db_companies.json';
  private readonly jsonUrlTransportations =
    'assets/json/db_transportations.json';
  private readonly jsonUrlSchedules = 'assets/json/db_schedule.json';
  private readonly jsonUrlComparisons = 'assets/json/db_comparison.json';
  private readonly url_sched: string;
  private readonly url_comp: string;
  private readonly url_client: string;
  private formData: any = null;
  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url_sched = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/clients`;
    this.url_comp = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/clients`;
    this.url_client = `${this.configService.URL_API}${this.configService.ENDPOINTS.clients}`;
  }

  getPorts(): Observable<Port[]> {
    return this.http.get<Port[]>(this.jsonUrlPorts);
  }
  getContainers(): Observable<Container[]> {
    return this.http.get<Container[]>(this.jsonUrlContainers);
  }
  getTransportations(): Observable<Transportation[]> {
    return this.http.get<Transportation[]>(this.jsonUrlTransportations);
  }
  getCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.jsonUrlCompanies);
  }

  saveForm(data: any) {
    this.formData = data;
  }

  getForm(): any {
    return this.formData;
  }

  clearForm() {
    this.formData = null;
  }

  /**
   * Toggles the favorite status of a schedule item
   * @param id The id of the schedule item to toggle
   * @returns An observable of ApiResponses<void> representing the result of the toggle operation
   */
  toggleFavoriteSchedule(id: string): Observable<ApiResponses<void>> {
    const url = `${this.url_client}/schedule-favorite/${id}`;
    return this.http
      .post<ApiResponses<void>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to favorite data');
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
   * Toggles the past status of a schedule item
   * @param id The id of the schedule item to toggle
   * @returns An observable of ApiResponses<void> representing the result of the toggle operation
   */
  togglePastSchedule(id: string): Observable<ApiResponses<void>> {
    const url = `${this.url_client}/schedule-past/${id}`;
    return this.http
      .post<ApiResponses<void>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to past data');
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

  // get data using pagination method for server-side processing Schedule
  getPaginatedDataSchedule(dtParams: any, filters: any): Observable<any> {
    const requestBody = {
      pagination: {
        pageNumber: dtParams.start / dtParams.length + 1,
        pageSize: dtParams.length,
        sortBy: dtParams.columns[dtParams.order[0].column].data,
        sortDirection: dtParams.order[0].dir.toUpperCase(),
      },
      // Filters (flattened to root level)
      portFromCode: filters?.portFromCode ?? null,
      portToCode: filters?.portToCode ?? null,
      dateDepart: filters?.dateDepart ?? null,
      dateArrive: filters?.dateArrive ?? null,
      companyId: filters?.companyId ?? null,
      //filters: filters ?? null // Add filters to the request
    };
    return this.http
      .post<any>(
        this.jsonUrlSchedules,
        requestBody,
        this.configService.httpOptions
      )
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

    /**
   * Toggles the favorite status of a schedule item
   * @param id The id of the schedule item to toggle
   * @returns An observable of ApiResponses<void> representing the result of the toggle operation
   */
  toggleFavoriteComparison(id: string): Observable<ApiResponses<void>> {
    const url = `${this.url_client}/comparison-favorite/${id}`;
    return this.http
      .post<ApiResponses<void>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to favorite data');
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
   * Toggles the past status of a comparison item
   * @param id The id of the comparison item to toggle
   * @returns An observable of ApiResponses<void> representing the result of the toggle operation
   */
  togglePastComparison(id: string): Observable<ApiResponses<void>> {
    const url = `${this.url_client}/comparison-past/${id}`;
    return this.http
      .post<ApiResponses<void>>(url, this.configService.httpOptions)
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error('Failed to past data');
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

// get data using pagination method for server-side processing Comparison
  getPaginatedDataComparison(dtParams: any, filters: any): Observable<any> {
    const requestBody = {
      pagination: {
        pageNumber: dtParams.start / dtParams.length + 1,
        pageSize: dtParams.length,
        sortBy: dtParams.columns[dtParams.order[0].column].data,
        sortDirection: dtParams.order[0].dir.toUpperCase(),
      },
      // Filters (flattened to root level)
      portFromCode: filters?.portFromCode ?? null,
      portToCode: filters?.portToCode ?? null,
      dateDepart: filters?.dateDepart ?? null,
      dateArrive: filters?.dateArrive ?? null,
      companyId: filters?.companyId ?? null,
      //filters: filters ?? null // Add filters to the request
    };
    return this.http
      .post<any>(
        this.jsonUrlComparisons,
        requestBody,
        this.configService.httpOptions
      )
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

