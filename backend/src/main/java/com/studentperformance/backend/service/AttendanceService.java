package com.studentperformance.backend.service;

import com.studentperformance.backend.entity.Attendance;
import com.studentperformance.backend.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AnalysisService analysisService;

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Attendance saveAttendance(Attendance attendance) {
        if (attendance.getAttendedClasses() > attendance.getTotalClasses()) {
            throw new IllegalArgumentException("Attended classes cannot exceed total classes");
        }
        if (attendance.getTotalClasses() > 0) {
            double pct = ((double) attendance.getAttendedClasses() / attendance.getTotalClasses()) * 100.0;
            attendance.setAttendancePercentage(Math.round(pct * 10.0) / 10.0);
        } else {
            attendance.setAttendancePercentage(0.0);
        }
        Attendance savedAtt = attendanceRepository.save(attendance);
        analysisService.generateAnalysisForStudent(attendance.getStudentId());
        return savedAtt;
    }

    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElse(null);
        if (attendance != null) {
            Long studentId = attendance.getStudentId();
            attendanceRepository.deleteById(id);
            analysisService.generateAnalysisForStudent(studentId);
        }
    }
}
