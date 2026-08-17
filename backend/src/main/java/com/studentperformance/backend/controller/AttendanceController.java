package com.studentperformance.backend.controller;

import com.studentperformance.backend.entity.Attendance;
import com.studentperformance.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
