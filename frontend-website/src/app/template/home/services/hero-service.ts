import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, of } from "rxjs";
import {
  Commodity,
  Company,
  Container,
  Port,
  Transportation,
} from "../../../modules/data-json";
import { ConfigService } from "../../../services/config/config";
import { ApiResponses } from "../../../modules/api-responses";
import { Comparison } from "../modules/comparison";

@Injectable({
  providedIn: "root",
})
export class HeroService {
  private readonly jsonUrlPorts = "assets/json/db_ports.json";
  private readonly jsonUrlContainers = "assets/json/db_containers.json";
  private readonly jsonUrlCompanies = "assets/json/db_companies.json";
  private readonly jsonUrlTransportations =
    "assets/json/db_transportations.json";
  private readonly jsonUrlSchedules = "assets/json/db_schedule.json";
  private readonly jsonUrlComparisons = "assets/json/db_comparison.json";
  private readonly jsonUrlCommodities = "assets/json/db_commodities.json";
  private readonly url_sched: string;
  private readonly url_comp: string;
  private readonly url_client: string;
  private readonly url_comp_create: string;
  private formData: any = null;
  private cargoFormData: any = null;
  private comp: Comparison | null = null;
  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url_sched = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/clients`;
    this.url_comp = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/clients`;
    this.url_comp_create = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/clients/create-comp-clients`;
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

  getCommodities(): Observable<Commodity[]> {
    return this.http.get<Commodity[]>(this.jsonUrlCommodities);
  }

  saveForm(data: any, check: number) {
    if (check == 1) {
      this.formData = data;
      localStorage.setItem("formData", JSON.stringify(data)); // save to localStorage
    } else {
      this.cargoFormData = data;
      localStorage.setItem("cargoFormData", JSON.stringify(data)); // save to localStorage
    }
  }

  getForm(check: number) {
    if (check == 2) {
      if (!this.cargoFormData) {
        const saved = localStorage.getItem("cargoFormData");
        if (saved) {
          this.cargoFormData = JSON.parse(saved); // restore from localStorage
        }
      }
      return this.cargoFormData;
    }
    if (check == 1) {
      if (!this.formData) {
        const saved = localStorage.getItem("formData");
        if (saved) {
          this.formData = JSON.parse(saved); // restore from localStorage
        }
      }
      return this.formData;
    }
  }

  clearForm(check: number) {
    if (check == 1) {
      this.formData = null;
      localStorage.removeItem("formData"); // remove from localStorage
    } else {
      this.cargoFormData = null;
      localStorage.removeItem("cargoFormData"); // remove from localStorage
    }
  }

  setComparison(comp: Comparison) {
    this.comp = comp;
  }

  getComparison() {
    return this.comp;
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
            throw new Error("Failed to favorite data");
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

  create(data: FormData): Observable<ApiResponses<void>> {
    return this.http
      .post<ApiResponses<void>>(
        this.url_comp_create,
        data,
        this.configService.httpOptionOnlyTokens
      )
      .pipe(
        map((response) => {
          if (!response.isSuccess) {
            throw new Error("Failed to create data");
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
            throw new Error("Failed to past data");
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
      },
      isCheapest: filters?.isCheapest ?? null,
      isDirect: filters?.isDirect ?? false,
      isFastest: filters?.isFastest ?? false,
      selectedPortFromSchedule: filters?.selectedPortFromSchedule ?? null,
      selectedPortToSchedule: filters?.selectedPortToSchedule ?? null,
      selectedCompany: filters?.selectedCompany ?? null,
      searchOn: filters?.searchOn ?? null,
      startDateSchedule: filters?.startDateSchedule ?? null,
      endDateSchedule: filters?.endDateSchedule ?? null,
      weeksAhead: filters?.weeksAhead ?? null,
    };
    return this.http
      .post<any>(this.url_sched, requestBody, this.configService.httpOptions)
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
            throw new Error("Failed to favorite data");
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
            throw new Error("Failed to past data");
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
  // In your heroService
  getPaginatedDataComparison(dtParams: any, filters: any): Observable<any> {
    // Remove pageNumber and pageSize from top level, only keep them in pagination object
    const requestBody = {
      pagination: {
        pageNumber: dtParams.start / dtParams.length + 1,
        pageSize: dtParams.length,
      },
      isCheapest: filters?.isCheapest ?? null,
      isDirect: filters?.isDirect ?? false,
      isFastest: filters?.isFastest ?? false,
      isIntervalMode: filters?.isIntervalMode ?? null,
      selectedContainer: filters?.selectedContainer ?? null,
      startDateComparison: filters?.startDateComparison ?? null,
      endDateComparison: filters?.endDateComparison ?? null,
      selectedPortFromComparison: filters?.selectedPortFromComparison ?? null,
      selectedPortToComparison: filters?.selectedPortToComparison ?? null,
      selectedTransportation: filters?.selectedTransportation ?? null,
    };
    return this.http
      .post<any>(this.url_comp, requestBody, this.configService.httpOptions)
      .pipe(
        map((response) => {
          // Adjust this mapping based on your actual response structure
          const customResponse = response;
          return {
            draw: dtParams.draw,
            recordsTotal: customResponse.response.totalElementCount,
            recordsFiltered: customResponse.response.totalElementCount,
            data: customResponse.response.content,
            totalPages: Math.ceil(
              customResponse.response.totalElementCount / dtParams.length
            ),
            currentPage: customResponse.response.pageNumber,
          };
        }),
        catchError((error) => {
          console.error("API Error:", error);
          console.error("Error details:", error.error);
          return of({
            draw: dtParams.draw,
            recordsTotal: 0,
            recordsFiltered: 0,
            data: [],
            totalPages: 0,
            currentPage: 1,
          });
        })
      );
  }
}
