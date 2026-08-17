package com.studentperformance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalStudents;
    private double averageMarksPercentage;
    private double averageAttendancePercentage;
    private long highRiskStudentsCount;
    private Map<String, Integer> gradeDistribution; // e.g. "A+": 5, "A": 12, ...
    private Map<String, Double> departmentAverages;
}
