export interface DashboardStats {
  totalAdminSchedules: number;
  totalAdminComparisons: number;
  totalAdminScoreSchedules: number;
  totalAdminScoreComparisons: number;
  totalUserScheduleTodays: number;
  totalUserComparisonTodays: number;
  totalUserScheduleYesterdays: number;
  totalUserComparisonYesterdays: number;
  scoreUserSchedules: number;
  scoreUserComparison: number;
}

export interface WeeklyScheduleData {
  week: string;
  schedules: number[];
}

export interface WeeklyComparisonData {
  week: string;
  comparisons: number[];
}
