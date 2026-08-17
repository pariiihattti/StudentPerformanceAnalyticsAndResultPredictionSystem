package com.studentperformance.backend.service;

import com.studentperformance.backend.dto.AuthRequest;
import com.studentperformance.backend.dto.AuthResponse;
import com.studentperformance.backend.entity.Admin;
import com.studentperformance.backend.entity.Teacher;
import com.studentperformance.backend.repository.AdminRepository;
import com.studentperformance.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private com.studentperformance.backend.repository.StudentRepository studentRepository;

    public AuthResponse login(AuthRequest request) {
        // Check Admin first
        Optional<Admin> adminOpt = adminRepository.findByUsername(request.getUsername());
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(request.getPassword())) {
                String dummyToken = "token_" + UUID.randomUUID().toString();
                return new AuthResponse(
                        dummyToken,
                        admin.getUsername(),
                        "Admin User",
                        "ADMIN",
                        true,
                        "Login successful",
                        admin.getId()
                );
            }
        }

        // Check Teacher
        Optional<Teacher> teacherOpt = teacherRepository.findByUsername(request.getUsername());
        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            if (teacher.getPassword().equals(request.getPassword())) {
                String dummyToken = "token_" + UUID.randomUUID().toString();
                return new AuthResponse(
                        dummyToken,
                        teacher.getUsername(),
                        teacher.getFullName(),
                        "TEACHER",
                        true,
                        "Login successful",
                        teacher.getId()
                );
            }
        }

        // Check Student (username = rollNumber)
        Optional<com.studentperformance.backend.entity.Student> studentOpt = studentRepository.findByRollNumber(request.getUsername());
        if (studentOpt.isPresent()) {
            com.studentperformance.backend.entity.Student student = studentOpt.get();
            String studentPass = student.getPassword() != null ? student.getPassword() : "student123";
            if (studentPass.equals(request.getPassword())) {
                String dummyToken = "token_" + UUID.randomUUID().toString();
                return new AuthResponse(
                        dummyToken,
                        student.getRollNumber(),
                        student.getName(),
                        "STUDENT",
                        true,
                        "Login successful",
                        student.getId()
                );
            }
        }

        return new AuthResponse(null, null, null, null, false, "Invalid username or password", null);
    }
}
