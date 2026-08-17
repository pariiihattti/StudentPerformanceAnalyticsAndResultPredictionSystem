package com.studentperformance.backend.repository;

import com.studentperformance.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    @Query("SELECT s FROM Student s WHERE " +
           "(:query IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.rollNumber) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:department IS NULL OR s.department = :department) AND " +
           "(:semester IS NULL OR s.semester = :semester)")
    List<Student> searchStudents(@Param("query") String query, 
                                 @Param("department") String department, 
                                 @Param("semester") Integer semester);

    java.util.Optional<Student> findByRollNumber(String rollNumber);
}
