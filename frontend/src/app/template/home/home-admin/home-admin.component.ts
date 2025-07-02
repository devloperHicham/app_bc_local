import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DashboardService } from '../../../services/dashboard.service';
import {
  DashboardStats,
  WeeklyComparisonData,
  WeeklyScheduleData,
} from '../../../modules/dashboard';

@Component({
  selector: 'app-home-admin',
  imports: [],
  templateUrl: './home-admin.component.html',
  styleUrl: './home-admin.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeAdminComponent {
  totalSchedule: number = 0;
  totalComparisons: number = 0;
  scoreSchedules: number = 0;
  scoreComparisons: number = 0;

  // Chart configs
  public chartOptionsWeeklySchedule: any = [];
  public chartOptionsWeeklyComparison: any = [];

  // Static weekdays
  public weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];

  constructor(private readonly dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.initializeChartOptions();
    this.loadScheduleStats();
    this.loadComparisonStats();
    this.loadWeeklyScheduleChart();
    this.loadWeeklyComparisonChart();
  }

  private loadScheduleStats(): void {
    this.dashboardService.getScheduleStats().subscribe({
      next: (stats: DashboardStats) => {
        this.totalSchedule = stats.totalAdminSchedules ?? 0;
        this.scoreSchedules = stats.totalAdminScoreSchedules ?? 0;
      },
      error: (err) => console.error('Schedule stats error:', err),
    });
  }

  private loadComparisonStats(): void {
    this.dashboardService.getComparisonStats().subscribe({
      next: (stats: DashboardStats) => {
        this.totalComparisons = stats.totalAdminComparisons ?? 0;
        this.scoreComparisons = stats.totalAdminScoreComparisons ?? 0;
      },
      error: (err) => console.error('Comparison stats error:', err),
    });
  }
  private initializeChartOptions(): void {
    // Common chart configuration
    const commonOptions = {
      animationEnabled: true,
      theme: 'light2',
      axisY: {
        title: 'Insertion',
        includeZero: true,
      },
      axisX: {
        title: 'les jours de la semaine',
        interval: 1,
        labelAngle: -45,
      },
      toolTip: {
        shared: true,
      },
      legend: {
        cursor: 'pointer',
        verticalAlign: 'top',
        horizontalAlign: 'center',
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
  }

  private loadWeeklyScheduleChart(): void {
    this.dashboardService.getGraphicScheduleStats().subscribe({
      next: (data: WeeklyScheduleData[]) => {
        this.updateScheduleChart(data);
      },
      error: (err) => {
        console.error('Weekly schedule chart error:');
      },
    });
  }

  private loadWeeklyComparisonChart(): void {
    this.dashboardService.getGraphicComparisonStats().subscribe({
      next: (data: WeeklyComparisonData[]) => {
        this.updateComparisonChart(data);
      },
      error: (err) => {
        console.error('Weekly comparison chart error:', err);
      },
    });
  }

  private updateScheduleChart(data: WeeklyScheduleData[]): void {
    const series = data.map((weekData) => ({
      type: 'line',
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
      type: 'column',
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
}
