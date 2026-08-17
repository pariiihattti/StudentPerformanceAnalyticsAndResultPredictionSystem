package com.studentperformance.backend.dto;

import com.studentperformance.backend.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResultDTO {
    private Student student;
    private double currentAverageMarks;
    private double currentAttendancePercentage;
    private double predictedFinalPercentage;
    private String predictedGrade;
    private String riskLevel; // LOW, MEDIUM, HIGH
    private List<String> recommendations;
}
