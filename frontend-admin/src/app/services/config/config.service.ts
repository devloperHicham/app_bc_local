import { HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NgxSpinnerService } from "ngx-spinner";
import { Observable, of, throwError } from "rxjs";
import Swal from "sweetalert2";
import { environment } from "../../../environments/environment.prod";

const access_token = localStorage.getItem("access_token");

@Injectable({
  providedIn: "root",
})
export class ConfigService {
  public readonly URL_API_AUTH =
    environment.baseUrl + "/api/v1/authentication/users/";
  public readonly URL_API = environment.baseUrl + "/api/v1/";

  public readonly ENDPOINTS = {
    users: "users",
    refresh: "refresh",
    logout: "logout",
    home: "home",
    login: "login",
    editPassword: "edit-password",
    companies: "companies",
    containers: "containers",
    gargos: "gargos",
    ports: "ports",
    transportations: "transportations",
    schedules: "schedules",
    settings: "settings",
    comparisons: "comparisons",
    faqs: "faqs",
    homeUsers: "home-users",
    homeAdmin: "home-admin",
    notAuthorized: "not-authorized",
  };
  public REMEMBER_ME_TIMEOUT = 86400; // 24 hours
  public SESSION_TIMEOUT = 3600; // 1 hour

  constructor(private readonly spinner: NgxSpinnerService) {}

  public httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      Authorization: "Bearer " + access_token,
    }),
  };
  public httpOptionImages = {
    headers: new HttpHeaders({
      "Content-Type": "image/*", // or 'image/*' for any image type
      Authorization: "Bearer " + access_token, // Make sure to get fresh token
    }),
    responseType: "blob" as const,
  };

  public httpOptionOnlyTokens = {
    headers: new HttpHeaders({
      Authorization: "Bearer " + access_token,
    }),
  };
  public httpOptionLogins = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
    }),
  };
  public httpOptionPdfs = {
    headers: new HttpHeaders({
      "Content-Type": "application/pdf",
      Authorization: "Bearer " + access_token,
    }),
    responseType: "blob", // Définissez responseType en dehors de headers
  };

  // Function to get today's date in the format YYYY-MM-DD
  getTodayDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
  }

  public handleError<T>(operation = "operation") {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(error.message));
    };
  }
  public showErrorAlert(message: string): void {
    Swal.fire({
      position: "top-end",
      icon: "error",
      title: message,
      showConfirmButton: false,
      timer: 2000,
    });
    this.spinner.hide();
  }

  public showSuccessAlert(message: string): void {
    Swal.fire({
      position: "top-end",
      icon: "success",
      title: message,
      showConfirmButton: false,
      timer: 2000,
    });
    this.spinner.hide();
  }
}

// Move the handleError function outside of the ConfigService class
export function handleError<T>(errorMessage: string, fallbackValue: T) {
  console.error(errorMessage); // Optionally log the error to a remote service
  return of(fallbackValue); // Return the fallback value
}
