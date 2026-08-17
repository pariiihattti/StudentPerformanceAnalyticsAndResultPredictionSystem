package com.studentperformance.backend.dto;

import com.studentperformance.backend.entity.Attendance;
import com.studentperformance.backend.entity.Mark;
import com.studentperformance.backend.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportDTO {
    private Student student;
    private List<Mark> marks;
    private List<Attendance> attendance;
    private double overallPercentage;
    private String overallGrade;
    private double overallAttendancePercentage;
    private PredictionResultDTO prediction;
}
