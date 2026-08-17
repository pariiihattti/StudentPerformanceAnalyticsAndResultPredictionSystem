package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
}
