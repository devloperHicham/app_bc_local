import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { ConfigService } from "./config/config.service";
import {
  DailyComparisonByUsersData,
  DailyScheduleByUsersData,
  DashboardStats,
  WeeklyComparisonByCompaniesData,
  WeeklyComparisonData,
  WeeklyScheduleByCompaniesData,
  WeeklyScheduleData,
} from "../modules/dashboard";

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  constructor(
    private readonly http: HttpClient,
    private readonly configService: ConfigService
  ) {}

  getScheduleStats(): Observable<DashboardStats> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/stats`;
    return this.http
      .get<DashboardStats>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultStats();
        }),
        catchError((error) => {
          console.error("Error fetching schedule stats:");
          return of(this.getDefaultStats());
        })
      );
  }

  getComparisonStats(): Observable<DashboardStats> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/stats`;
    return this.http
      .get<DashboardStats>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultStats();
        }),
        catchError((error) => {
          console.error("Error fetching comparison stats:");
          return of(this.getDefaultStats());
        })
      );
  }

  getGraphicScheduleStats(): Observable<WeeklyScheduleData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/graphics`;
    return this.http
      .get<WeeklyScheduleData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultGraphScheduleData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic schedule data:");
          return of(this.getDefaultGraphScheduleData());
        })
      );
  }

  getGraphicComparisonStats(): Observable<WeeklyComparisonData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/graphics`;
    return this.http
      .get<WeeklyComparisonData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultGraphComparisonByCompaniesData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic comparison data:");
          return of(this.getDefaultGraphComparisonData());
        })
      );
  }

  getGraphicScheduleByCompaniesStats(): Observable<WeeklyScheduleByCompaniesData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/graphic-companies`;
    return this.http
      .get<WeeklyScheduleByCompaniesData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultGraphScheduleByCompaniesData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic schedule data:");
          return of(this.getDefaultGraphScheduleByCompaniesData());
        })
      );
  }

  getGraphicComparisonByCompaniesStats(): Observable<WeeklyComparisonByCompaniesData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/graphic-companies`;
    return this.http
      .get<WeeklyComparisonByCompaniesData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          return data || this.getDefaultGraphComparisonByCompaniesData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic comparison data:");
          return of(this.getDefaultGraphComparisonByCompaniesData());
        })
      );
  }

  getGraphicComparisonByUsersStats(): Observable<DailyComparisonByUsersData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.comparisons}/graphic-users`;
    return this.http
      .get<DailyComparisonByUsersData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          console.log("Data fetched:", data);
          return data || [];
        }),
        catchError((error) => {
          console.log("Data fetched:", error);
          console.error("Error fetching graphic comparison data:");
          return of([]);
        })
      );
  }

    getGraphicScheduleByUsersStats(): Observable<DailyScheduleByUsersData[]> {
    const urls = `${this.configService.URL_API}${this.configService.ENDPOINTS.schedules}/graphic-users`;
    return this.http
      .get<DailyScheduleByUsersData[]>(urls, this.configService.httpOptions)
      .pipe(
        map((data) => {
          console.log("Data fetched:", data);
          return data || [];
        }),
        catchError((error) => {
          console.log("Data fetched:", error);
          console.error("Error fetching graphic schedule data:");
          return of([]);
        })
      );
  }
  // Helper method for default stats
  private getDefaultStats(): DashboardStats {
    return {
      totalAdminSchedules: 0,
      totalAdminComparisons: 0,
      totalAdminScoreSchedules: 0,
      totalAdminScoreComparisons: 0,
      totalUserScheduleTodays: 0,
      totalUserComparisonTodays: 0,
      totalUserScheduleYesterdays: 0,
      totalUserComparisonYesterdays: 0,
      scoreUserSchedules: 0,
      scoreUserComparison: 0,
    };
  }

  private getDefaultGraphScheduleData(): WeeklyScheduleData[] {
    return [
      {
        week: "Current Week Schedule",
        schedules: [0, 0, 0, 0, 0, 0, 0],
      },
    ];
  }

  private getDefaultGraphComparisonData(): WeeklyComparisonData[] {
    return [
      {
        week: "Current Week Comparison",
        comparisons: [0, 0, 0, 0, 0, 0, 0],
      },
    ];
  }

    private getDefaultGraphScheduleByCompaniesData(): WeeklyScheduleByCompaniesData[] {
    return [
      {
        companyName: "Current company Schedule",
        schedules: [0, 0, 0, 0, 0, 0, 0],
      },
    ];
  }

  private getDefaultGraphComparisonByCompaniesData(): WeeklyComparisonByCompaniesData[] {
    return [
      {
        companyName: "Current Company Comparison",
        comparisons: [0, 0, 0, 0, 0, 0, 0],
      },
    ];
  }
}
