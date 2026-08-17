package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.ResultPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResultPredictionRepository extends JpaRepository<ResultPrediction, Long> {
    Optional<ResultPrediction> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
}
