import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { ConfigService } from "./config/config.service";
import {
  DashboardStats,
  WeeklyComparisonData,
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
          console.log("Schedule stats response:", data);
          return data || this.getDefaultStats();
        }),
        catchError((error) => {
          console.error("Error fetching schedule stats:", error);
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
          console.log("Comparison stats response:", data);
          return data || this.getDefaultStats();
        }),
        catchError((error) => {
          console.error("Error fetching comparison stats:", error);
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
          console.log("Graphic schedule data:", data);
          return data || this.getDefaultGraphScheduleData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic schedule data:", error);
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
          console.log("Graphic comparison data:", data);
          return data || this.getDefaultGraphComparisonData();
        }),
        catchError((error) => {
          console.error("Error fetching graphic comparison data:", error);
          return of(this.getDefaultGraphComparisonData());
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
}
