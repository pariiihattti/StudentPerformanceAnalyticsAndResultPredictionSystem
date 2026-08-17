package com.studentperformance.backend.service;

import com.studentperformance.backend.entity.*;
import com.studentperformance.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private PerformanceAnalysisRepository performanceAnalysisRepository;

    @Autowired
    private ResultPredictionRepository resultPredictionRepository;

    public void generateAnalysisForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return;

        // 1. Calculate Average Marks
        List<Mark> marks = markRepository.findByStudentId(studentId);
        double totalMarks = 0;
        for (Mark mark : marks) {
            totalMarks += mark.getTotalMarks();
        }
        double averageMarks = marks.isEmpty() ? 0 : totalMarks / marks.size();

        // 2. Calculate Average Attendance %
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        double totalAttendancePct = 0;
        for (Attendance att : attendances) {
            totalAttendancePct += att.getAttendancePercentage();
        }
        double averageAttendance = attendances.isEmpty() ? 0 : totalAttendancePct / attendances.size();

        // 3. Determine Risk Level / Performance Level
        String riskLevel = "LOW"; // Default
        String predictedGrade = "A"; // Default prediction

        // Simple rules based on average marks (can be extended based on user feedback)
        if (averageMarks >= 90) {
            riskLevel = "LOW";
            predictedGrade = "Excellent";
        } else if (averageMarks >= 75) {
            riskLevel = "LOW";
            predictedGrade = "Good";
        } else if (averageMarks >= 50) {
            riskLevel = "MEDIUM";
            predictedGrade = "Average";
        } else {
            riskLevel = "HIGH";
            predictedGrade = "Poor";
        }

        // Adjust for poor attendance
        if (averageAttendance < 60) {
            riskLevel = "HIGH";
            if (predictedGrade.equals("Excellent") || predictedGrade.equals("Good")) {
                 predictedGrade = "Average"; // Downgrade prediction due to poor attendance
            } else {
                 predictedGrade = "Poor";
            }
        }

        // 4. Save Performance Analysis
        PerformanceAnalysis analysis = performanceAnalysisRepository.findByStudentId(studentId).orElse(new PerformanceAnalysis());
        analysis.setStudent(student);
        analysis.setOverallScore(averageMarks);
        analysis.setRiskLevel(riskLevel);
        performanceAnalysisRepository.save(analysis);

        // 5. Save Result Prediction
        ResultPrediction prediction = resultPredictionRepository.findByStudentId(studentId).orElse(new ResultPrediction());
        prediction.setStudent(student);
        prediction.setPredictedGrade(predictedGrade);
        prediction.setConfidenceScore(85.0); // Dummy confidence score for MVP
        resultPredictionRepository.save(prediction);
    }
}
