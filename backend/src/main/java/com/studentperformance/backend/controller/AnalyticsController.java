package com.studentperformance.backend.controller;

import com.studentperformance.backend.dto.DashboardStatsDTO;
import com.studentperformance.backend.dto.PredictionResultDTO;
import com.studentperformance.backend.dto.StudentReportDTO;
import com.studentperformance.backend.service.PredictionEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private PredictionEngineService predictionEngineService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(predictionEngineService.getDashboardStats());
    }

    @GetMapping("/predict/{studentId}")
    public ResponseEntity<PredictionResultDTO> predictStudentPerformance(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(predictionEngineService.predictStudentPerformance(studentId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/report/{studentId}")
    public ResponseEntity<StudentReportDTO> generateStudentReport(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(predictionEngineService.generateStudentReport(studentId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
