package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);
}
