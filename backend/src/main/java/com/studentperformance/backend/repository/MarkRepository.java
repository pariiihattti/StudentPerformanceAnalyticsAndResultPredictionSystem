package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
}
