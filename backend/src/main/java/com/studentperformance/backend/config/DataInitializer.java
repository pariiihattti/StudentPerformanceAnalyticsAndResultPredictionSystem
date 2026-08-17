package com.studentperformance.backend.config;

import com.studentperformance.backend.entity.Admin;
import com.studentperformance.backend.entity.Teacher;
import com.studentperformance.backend.repository.AdminRepository;
import com.studentperformance.backend.repository.TeacherRepository;
import com.studentperformance.backend.repository.PerformanceAnalysisRepository;
import com.studentperformance.backend.repository.ResultPredictionRepository;
import com.studentperformance.backend.entity.PerformanceAnalysis;
import com.studentperformance.backend.entity.ResultPrediction;
import com.studentperformance.backend.entity.Attendance;
import com.studentperformance.backend.entity.Mark;
import com.studentperformance.backend.entity.Student;
import com.studentperformance.backend.repository.AttendanceRepository;
import com.studentperformance.backend.repository.MarkRepository;
import com.studentperformance.backend.repository.StudentRepository;
import com.studentperformance.backend.service.MarkService;
import com.studentperformance.backend.service.AnalysisService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            AdminRepository adminRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            MarkRepository markRepository,
            AttendanceRepository attendanceRepository,
            PerformanceAnalysisRepository analysisRepository,
            ResultPredictionRepository predictionRepository,
            AnalysisService analysisService
    ) {
        return args -> {
            // Seed Admin & Teacher Accounts
            Admin admin = null;
            if (adminRepository.count() == 0) {
                admin = adminRepository.save(new Admin(null, "admin", "admin123", "admin@university.edu", null));
            } else {
                admin = adminRepository.findByUsername("admin").orElse(null);
            }
            
            if (teacherRepository.count() == 0 && admin != null) {
                teacherRepository.save(new Teacher(null, admin, "teacher", "teacher123", "Prof. Robert Smith", "robert.smith@university.edu", null));
            }

            // Seed Dummy Student Records for Database Testing
            if (studentRepository.count() == 0 && admin != null) {
                Student s1 = studentRepository.save(new Student(null, admin, "CS202401", "Aarav Sharma", "aarav.sharma@example.com", "Computer Science", 6, "2024", "student123"));
                Student s2 = studentRepository.save(new Student(null, admin, "CS202402", "Priya Patel", "priya.patel@example.com", "Computer Science", 6, "2024", "student123"));
                Student s3 = studentRepository.save(new Student(null, admin, "IT202401", "Rohan Verma", "rohan.verma@example.com", "Information Technology", 4, "2024", "student123"));
                Student s4 = studentRepository.save(new Student(null, admin, "IT202402", "Ananya Sen", "ananya.sen@example.com", "Information Technology", 4, "2024", "student123"));
                Student s5 = studentRepository.save(new Student(null, admin, "EC202401", "Vikram Singh", "vikram.singh@example.com", "Electronics & Comm", 6, "2024", "student123"));
                Student s6 = studentRepository.save(new Student(null, admin, "CS202403", "Neha Gupta", "neha.gupta@example.com", "Computer Science", 6, "2024", "student123"));
                Student s7 = studentRepository.save(new Student(null, admin, "ME202401", "Kabir Mehta", "kabir.mehta@example.com", "Mechanical Eng", 2, "2024", "student123"));
                Student s8 = studentRepository.save(new Student(null, admin, "CE202401", "Diya Roy", "diya.roy@example.com", "Civil Eng", 2, "2024", "student123"));
                Student s9 = studentRepository.save(new Student(null, admin, "CS202404", "Arjun Reddy", "arjun.reddy@example.com", "Computer Science", 8, "2024", "student123"));
                Student s10 = studentRepository.save(new Student(null, admin, "IT202403", "Kavya Nair", "kavya.nair@example.com", "Information Technology", 8, "2024", "student123"));

                // Seed Subject Marks
                saveMark(markRepository, s1.getId(), "Data Structures", 36, 52); // 88 (Grade A)
                saveMark(markRepository, s1.getId(), "Database Systems", 35, 55); // 90 (Grade A+)
                saveMark(markRepository, s1.getId(), "Web Development", 38, 56); // 94 (Grade A+)

                saveMark(markRepository, s2.getId(), "Data Structures", 25, 38); // 63
                saveMark(markRepository, s2.getId(), "Database Systems", 28, 40); // 68

                saveMark(markRepository, s3.getId(), "Java Programming", 15, 22); // 37 (Grade F - At Risk)
                saveMark(markRepository, s3.getId(), "Computer Networks", 18, 25); // 43

                saveMark(markRepository, s4.getId(), "Java Programming", 32, 48); // 80
                saveMark(markRepository, s4.getId(), "Computer Networks", 34, 50); // 84

                saveMark(markRepository, s5.getId(), "Digital Electronics", 30, 42); // 72
                saveMark(markRepository, s5.getId(), "Microprocessors", 28, 44); // 72

                saveMark(markRepository, s6.getId(), "Data Structures", 39, 58); // 97
                saveMark(markRepository, s6.getId(), "Database Systems", 38, 57); // 95

                saveMark(markRepository, s7.getId(), "Thermodynamics", 24, 36); // 60
                saveMark(markRepository, s8.getId(), "Structural Engineering", 31, 49); // 80
                saveMark(markRepository, s9.getId(), "Artificial Intelligence", 37, 55); // 92
                saveMark(markRepository, s10.getId(), "Cloud Computing", 33, 47); // 80

                // Seed Attendance Logs
                saveAttendance(attendanceRepository, s1.getId(), "Data Structures", 40, 38); // 95%
                saveAttendance(attendanceRepository, s1.getId(), "Database Systems", 40, 36); // 90%

                saveAttendance(attendanceRepository, s2.getId(), "Data Structures", 40, 30); // 75%
                saveAttendance(attendanceRepository, s2.getId(), "Database Systems", 40, 31); // 77.5%

                saveAttendance(attendanceRepository, s3.getId(), "Java Programming", 40, 20); // 50% (Low Attendance Alert)
                saveAttendance(attendanceRepository, s3.getId(), "Computer Networks", 40, 22); // 55%

                saveAttendance(attendanceRepository, s4.getId(), "Java Programming", 40, 36);
                saveAttendance(attendanceRepository, s4.getId(), "Computer Networks", 40, 37);

                saveAttendance(attendanceRepository, s5.getId(), "Digital Electronics", 40, 34);
                saveAttendance(attendanceRepository, s6.getId(), "Data Structures", 40, 39);
                saveAttendance(attendanceRepository, s7.getId(), "Thermodynamics", 40, 32);
                saveAttendance(attendanceRepository, s8.getId(), "Structural Engineering", 40, 38);
                saveAttendance(attendanceRepository, s9.getId(), "Artificial Intelligence", 40, 39);
                saveAttendance(attendanceRepository, s10.getId(), "Cloud Computing", 40, 35);
                
                // Generate Analysis & Predictions for seeded data
                for (Student student : studentRepository.findAll()) {
                    analysisService.generateAnalysisForStudent(student.getId());
                }
            }
        };
    }

    private void saveMark(MarkRepository repo, Long studentId, String subject, double internal, double external) {
        double total = internal + external;
        repo.save(new Mark(null, studentId, subject, internal, external, total, MarkService.calculateGrade(total)));
    }

    private void saveAttendance(AttendanceRepository repo, Long studentId, String subject, int total, int attended) {
        double pct = total > 0 ? ((double) attended / total) * 100.0 : 0.0;
        repo.save(new Attendance(null, studentId, subject, total, attended, Math.round(pct * 10.0) / 10.0));
    }
}
