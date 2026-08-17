package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.PerformanceAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerformanceAnalysisRepository extends JpaRepository<PerformanceAnalysis, Long> {
    Optional<PerformanceAnalysis> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
}
