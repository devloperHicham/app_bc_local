import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, of } from "rxjs";
import {
  Company,
  Container,
  Port,
  Transportation,
} from "../../../modules/data-json";
import { Comparison } from "../modules/comparison";
import { ApiResponses } from "../../../modules/api-responses";
import { ConfigService } from "../../../services/config/config";

@Injectable({
  providedIn: "root",
})
export class HeroSerice {
  private readonly jsonUrlPorts = "assets/json/db_ports.json";
  private readonly jsonUrlContainers = "assets/json/db_containers.json";
  private readonly jsonUrlCompanies = "assets/json/db_companies.json";
  private readonly jsonUrlTransportations =
    "assets/json/db_transportations.json";
  private readonly url: string;
  private formData: any = null;
  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {
    this.url = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/clients`;
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

  // get data using pagination method for server-side processing
  getPaginatedData(dtParams: any, filters: any): Observable<any> {
    const requestBody = {
      pagination: {
        pageNumber: dtParams.start / dtParams.length + 1,
        pageSize: dtParams.length,
        sortBy: dtParams.columns[dtParams.order[0].column].data,
        sortDirection: dtParams.order[0].dir.toUpperCase(),
      },
      search: dtParams.search.value ?? null, // Pass search string to backend
      // Filters (flattened to root level)
      portFromId: filters?.portFromId ?? null,
      portToId: filters?.portToId ?? null,
      dateDepart: filters?.dateDepart ?? null,
      dateArrive: filters?.dateArrive ?? null,
      companyId: filters?.companyId ?? null,
      //filters: filters ?? null // Add filters to the request
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
