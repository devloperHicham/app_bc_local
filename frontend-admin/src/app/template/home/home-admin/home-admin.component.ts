import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from "@angular/core";
import { DashboardService } from "../../../services/dashboard.service";
import {
  DailyComparisonByUsersData,
  DailyScheduleByUsersData,
  DashboardStats,
  WeeklyComparisonByCompaniesData,
  WeeklyComparisonData,
  WeeklyScheduleByCompaniesData,
  WeeklyScheduleData,
} from "../../../modules/dashboard";
import { CommonModule } from "@angular/common";
import { CanvasJSAngularChartsModule } from "@canvasjs/angular-charts";

@Component({
  selector: "app-home-admin",
  imports: [CommonModule, CanvasJSAngularChartsModule],
  templateUrl: "./home-admin.component.html",
  styleUrl: "./home-admin.component.scss",
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeAdminComponent implements OnInit {
  totalSchedule: number = 0;
  totalComparisons: number = 0;
  scoreSchedules: number = 0;
  scoreComparisons: number = 0;

  // Chart configs
  public chartOptionsWeeklySchedule: any = [];
  public chartOptionsWeeklyComparison: any = [];
  public chartOptionsWeeklyScheduleByCompany: any = [];
  public chartOptionsWeeklyComparisonByCompany: any = [];
  public chartOptionsDailylyScheduleByUser: any = [];
  public chartOptionsDailyComparisonByUser: any = [];

  // Static weekdays
  public weekDays: string[] = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"];

  constructor(private readonly dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.initializeChartOptions();
    this.loadScheduleStats();
    this.loadComparisonStats();
    this.loadWeeklyScheduleChart();
    this.loadWeeklyComparisonChart();
    this.loadWeeklySchedulByCompaniesChart();
    this.loadWeeklyComparisonByCompaniesChart();
    this.loadDailyScheduleByUserChart();
    this.loadDailyComparisonByUserChart();
  }

  private loadScheduleStats(): void {
    this.dashboardService.getScheduleStats().subscribe({
      next: (stats: DashboardStats) => {
        this.totalSchedule = stats.totalAdminSchedules ?? 0;
        this.scoreSchedules = stats.totalAdminScoreSchedules ?? 0;
      },
      error: (err) => console.error("Schedule stats error:"),
    });
  }

  private loadComparisonStats(): void {
    this.dashboardService.getComparisonStats().subscribe({
      next: (stats: DashboardStats) => {
        this.totalComparisons = stats.totalAdminComparisons ?? 0;
        this.scoreComparisons = stats.totalAdminScoreComparisons ?? 0;
      },
      error: (err) => console.error("Comparison stats error:"),
    });
  }
  private initializeChartOptions(): void {
    // Common chart configuration
    const commonOptions = {
      animationEnabled: true,
      theme: "light2",
      axisY: {
        title: "Insertion",
        includeZero: true,
      },
      axisX: {
        title: "les jours de la semaine",
        interval: 1,
        labelAngle: -45,
      },
      toolTip: {
        shared: true,
      },
      legend: {
        cursor: "pointer",
        verticalAlign: "top",
        horizontalAlign: "center",
      },
    };
    const commonOptionUsers = {
      animationEnabled: true,
      theme: "light2",
      axisY: {
        title: "Insertion",
        includeZero: true,
      },
      axisX: {
        title: "les listes des utilisateurs",
        interval: 1,
        labelAngle: -45,
      },
      toolTip: {
        shared: true,
      },
      legend: {
        cursor: "pointer",
        verticalAlign: "top",
        horizontalAlign: "center",
      },
    };
    // Initialize with empty data - will be updated when data loads
    this.chartOptionsWeeklySchedule = {
      ...commonOptions,
      data: [],
    };

    this.chartOptionsWeeklyComparison = {
      ...commonOptions,
      data: [],
    };

    this.chartOptionsWeeklyScheduleByCompany = {
      ...commonOptions,
      data: [],
    };

    this.chartOptionsWeeklyComparisonByCompany = {
      ...commonOptions,
      data: [],
    };

    this.chartOptionsDailylyScheduleByUser = {
      ...commonOptionUsers,
      data: [],
    };

    this.chartOptionsDailyComparisonByUser = {
      ...commonOptionUsers,
      data: [],
    };
  }

  private loadWeeklyScheduleChart(): void {
    this.dashboardService.getGraphicScheduleStats().subscribe({
      next: (data: WeeklyScheduleData[]) => {
        this.updateScheduleChart(data);
      },
      error: (err) => {
        console.error("Weekly schedule chart error:");
      },
    });
  }

  private loadWeeklyComparisonChart(): void {
    this.dashboardService.getGraphicComparisonStats().subscribe({
      next: (data: WeeklyComparisonData[]) => {
        this.updateComparisonChart(data);
      },
      error: (err) => {
        console.error("Weekly comparison chart error:");
      },
    });
  }

  private loadWeeklyComparisonByCompaniesChart(): void {
    this.dashboardService.getGraphicComparisonByCompaniesStats().subscribe({
      next: (data: WeeklyComparisonByCompaniesData[]) => {
        this.updateComparisonByCompaniesChart(data);
      },
      error: (err) => {
        console.error("Weekly comparison chart error:");
      },
    });
  }

  private loadWeeklySchedulByCompaniesChart(): void {
    this.dashboardService.getGraphicScheduleByCompaniesStats().subscribe({
      next: (data: WeeklyScheduleByCompaniesData[]) => {
        this.updateScheduleByCompaniesChart(data);
      },
      error: (err) => {
        console.error("Weekly comparison chart error:");
      },
    });
  }
  private updateScheduleChart(data: WeeklyScheduleData[]): void {
    const series = data.map((weekData) => ({
      type: "line",
      name: weekData.week,
      showInLegend: true,
      dataPoints: this.weekDays.map((day, index) => ({
        label: day,
        y: weekData.schedules[index] || 0,
      })),
    }));

    this.chartOptionsWeeklySchedule = {
      ...this.chartOptionsWeeklySchedule,
      data: series,
    };
  }

  private updateComparisonChart(data: WeeklyComparisonData[]): void {
    const series = data.map((weekData) => ({
      type: "column",
      name: weekData.week,
      showInLegend: true,
      dataPoints: this.weekDays.map((day, index) => ({
        label: day,
        y: weekData.comparisons[index] || 0,
      })),
    }));

    this.chartOptionsWeeklyComparison = {
      ...this.chartOptionsWeeklyComparison,
      data: series,
    };
  }

  private updateComparisonByCompaniesChart(
    data: WeeklyComparisonByCompaniesData[]
  ): void {
    const series = data.map((companyData) => ({
      type: "column",
      name: companyData.companyName,
      showInLegend: true,
      dataPoints: this.weekDays.map((day, i) => ({
        label: day, // Grouped by day
        y: companyData.comparisons[i] || 0,
        toolTipContent: `${companyData.companyName} - ${day}: {y} insertions`,
      })),
    }));

    this.chartOptionsWeeklyComparisonByCompany = {
      ...this.chartOptionsWeeklyComparisonByCompany,
      data: series,
    };
  }
  private updateScheduleByCompaniesChart(
    data: WeeklyScheduleByCompaniesData[]
  ): void {
    const series = data.map((companyData) => ({
      type: "column", // or "line" for line graph
      name: companyData.companyName,
      showInLegend: true,
      dataPoints: this.weekDays.map((day, index) => ({
        label: day,
        y: companyData.schedules[index] || 0,
        toolTipContent: `${companyData.companyName} - ${day}: {y} insertions`,
      })),
    }));

    this.chartOptionsWeeklyScheduleByCompany = {
      ...this.chartOptionsWeeklyScheduleByCompany,
      data: series,
    };
  }

  private loadDailyScheduleByUserChart(): void {
    this.dashboardService.getGraphicScheduleByUsersStats().subscribe({
      next: (data: DailyScheduleByUsersData[]) => {
        this.chartOptionsDailylyScheduleByUser = {
          animationEnabled: true,
          title: {
            text: "Schedules par Utilisateur",
          },
          data: [
            {
              type: "pie",
              startAngle: 240,
              indexLabel: "{label} - {y}",
              toolTipContent: "<b>{label}</b>: {y} insertions",
              dataPoints: data.map((user) => ({
                y: user.schedules,
                label: user.userName,
              })),
            },
          ],
        };
      },
      error: () => console.error("Erreur chargement schedules utilisateur."),
    });
  }

  private loadDailyComparisonByUserChart(): void {
    this.dashboardService.getGraphicComparisonByUsersStats().subscribe({
      next: (data: DailyComparisonByUsersData[]) => {
        this.chartOptionsDailyComparisonByUser = {
          animationEnabled: true,
          title: {
            text: "Comparaisons par Utilisateur",
          },
          data: [
            {
              type: "pie",
              startAngle: 240,
              indexLabel: "{label} - {y}",
              toolTipContent: "<b>{label}</b>: {y} comparaisons",
              dataPoints: data.map((user) => ({
                y: user.comparisons,
                label: user.userName,
              })),
            },
          ],
        };
      },
      error: () => console.error("Erreur chargement comparisons utilisateur."),
    });
  }
}
