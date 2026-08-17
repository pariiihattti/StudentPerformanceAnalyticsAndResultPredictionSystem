package com.studentperformance.backend.service;

import com.studentperformance.backend.entity.Student;
import com.studentperformance.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import com.studentperformance.backend.repository.MarkRepository;
import com.studentperformance.backend.repository.AttendanceRepository;
import com.studentperformance.backend.repository.PerformanceAnalysisRepository;
import com.studentperformance.backend.repository.ResultPredictionRepository;

@Service
public class StudentService {

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

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setRollNumber(studentDetails.getRollNumber());
        student.setDepartment(studentDetails.getDepartment());
        student.setSemester(studentDetails.getSemester());
        student.setBatchYear(studentDetails.getBatchYear());

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        markRepository.deleteByStudentId(id);
        attendanceRepository.deleteByStudentId(id);
        performanceAnalysisRepository.deleteByStudentId(id);
        resultPredictionRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String query, String department, Integer semester) {
        return studentRepository.searchStudents(
                (query != null && !query.trim().isEmpty()) ? query.trim() : null,
                (department != null && !department.trim().isEmpty()) ? department.trim() : null,
                semester
        );
    }
}
