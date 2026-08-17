package com.studentperformance.backend.service;

import com.studentperformance.backend.dto.DashboardStatsDTO;
import com.studentperformance.backend.dto.PredictionResultDTO;
import com.studentperformance.backend.dto.StudentReportDTO;
import com.studentperformance.backend.entity.Attendance;
import com.studentperformance.backend.entity.Mark;
import com.studentperformance.backend.entity.Student;
import com.studentperformance.backend.repository.AttendanceRepository;
import com.studentperformance.backend.repository.MarkRepository;
import com.studentperformance.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PredictionEngineService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public PredictionResultDTO predictStudentPerformance(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        List<Mark> marks = markRepository.findByStudentId(studentId);
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);

        double avgMarks = marks.stream()
                .mapToDouble(Mark::getTotalMarks)
                .average()
                .orElse(0.0);

        double avgAttendance = attendances.stream()
                .mapToDouble(Attendance::getAttendancePercentage)
                .average()
                .orElse(0.0);

        // Result Prediction Algorithm: Weighted calculation based on marks & attendance
        // Predicted Final % = (Internal Marks * 0.55) + (Attendance % * 0.45)
        double predictedPercentage;
        if (marks.isEmpty() && attendances.isEmpty()) {
            predictedPercentage = 65.0; // default baseline
        } else if (marks.isEmpty()) {
            predictedPercentage = avgAttendance;
        } else if (attendances.isEmpty()) {
            predictedPercentage = avgMarks;
        } else {
            predictedPercentage = (avgMarks * 0.60) + (avgAttendance * 0.40);
        }

        predictedPercentage = Math.round(predictedPercentage * 10.0) / 10.0;
        String predictedGrade = MarkService.calculateGrade(predictedPercentage);

        String riskLevel;
        List<String> recommendations = new ArrayList<>();

        if (predictedPercentage < 50.0 || avgAttendance < 65.0) {
            riskLevel = "HIGH";
            recommendations.add("Critical: Attendance or internal test scores are below minimum passing thresholds.");
            recommendations.add("Schedule one-on-one remedial coaching sessions.");
            recommendations.add("Contact parents/guardians regarding academic performance concern.");
        } else if (predictedPercentage < 70.0 || avgAttendance < 75.0) {
            riskLevel = "MEDIUM";
            recommendations.add("Moderate risk detected. Attendance should be improved above 75%.");
            recommendations.add("Focus on subject areas with lower test scores.");
        } else {
            riskLevel = "LOW";
            recommendations.add("Student is performing consistently well.");
            recommendations.add("Encourage participation in advanced assignments and project competitions.");
        }

        return new PredictionResultDTO(
                student,
                Math.round(avgMarks * 10.0) / 10.0,
                Math.round(avgAttendance * 10.0) / 10.0,
                predictedPercentage,
                predictedGrade,
                riskLevel,
                recommendations
        );
    }

    public DashboardStatsDTO getDashboardStats() {
        List<Student> students = studentRepository.findAll();
        long totalStudents = students.size();

        double totalMarksSum = 0;
        double totalAttendanceSum = 0;
        long highRiskCount = 0;

        Map<String, Integer> gradeDist = new HashMap<>();
        gradeDist.put("A+", 0);
        gradeDist.put("A", 0);
        gradeDist.put("B", 0);
        gradeDist.put("C", 0);
        gradeDist.put("D", 0);
        gradeDist.put("F", 0);

        Map<String, List<Double>> deptScores = new HashMap<>();

        for (Student s : students) {
            PredictionResultDTO pred = predictStudentPerformance(s.getId());
            totalMarksSum += pred.getCurrentAverageMarks();
            totalAttendanceSum += pred.getCurrentAttendancePercentage();

            if ("HIGH".equals(pred.getRiskLevel())) {
                highRiskCount++;
            }

            gradeDist.put(pred.getPredictedGrade(), gradeDist.getOrDefault(pred.getPredictedGrade(), 0) + 1);

            String dept = s.getDepartment() != null ? s.getDepartment() : "General";
            deptScores.computeIfAbsent(dept, k -> new ArrayList<>()).add(pred.getPredictedFinalPercentage());
        }

        double avgMarks = totalStudents > 0 ? (totalMarksSum / totalStudents) : 0.0;
        double avgAttendance = totalStudents > 0 ? (totalAttendanceSum / totalStudents) : 0.0;

        Map<String, Double> deptAverages = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : deptScores.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            deptAverages.put(entry.getKey(), Math.round(avg * 10.0) / 10.0);
        }

        return new DashboardStatsDTO(
                totalStudents,
                Math.round(avgMarks * 10.0) / 10.0,
                Math.round(avgAttendance * 10.0) / 10.0,
                highRiskCount,
                gradeDist,
                deptAverages
        );
    }

    public StudentReportDTO generateStudentReport(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        List<Mark> marks = markRepository.findByStudentId(studentId);
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        PredictionResultDTO prediction = predictStudentPerformance(studentId);

        double overallPct = marks.stream().mapToDouble(Mark::getTotalMarks).average().orElse(0.0);
        double overallAtt = attendances.stream().mapToDouble(Attendance::getAttendancePercentage).average().orElse(0.0);

        return new StudentReportDTO(
                student,
                marks,
                attendances,
                Math.round(overallPct * 10.0) / 10.0,
                MarkService.calculateGrade(overallPct),
                Math.round(overallAtt * 10.0) / 10.0,
                prediction
        );
    }
}
