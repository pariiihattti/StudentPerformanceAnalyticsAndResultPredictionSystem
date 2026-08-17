-- ============================================================
-- Student Performance Analytics & Result Prediction System
-- Database: student_performance_db
-- ============================================================

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS student_performance_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE student_performance_db;

-- ============================================================
-- TABLE 1: admin (System Administrators)
-- ============================================================
CREATE TABLE IF NOT EXISTS admin (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE 2: teacher (Faculty Members)
-- ============================================================
CREATE TABLE IF NOT EXISTS teacher (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id      BIGINT,
    username      VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255),
    email         VARCHAR(255),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE SET NULL
);

-- ============================================================
-- TABLE 3: students (Enrolled student profiles)
-- ============================================================
CREATE TABLE IF NOT EXISTS students (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id        BIGINT,
    roll_number     VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    email           VARCHAR(255),
    department      VARCHAR(100),
    semester        INT CHECK (semester BETWEEN 1 AND 8),
    batch_year      VARCHAR(10),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE SET NULL
);

-- ============================================================
-- TABLE 4: marks (Subject-wise assessment scores)
-- ============================================================
CREATE TABLE IF NOT EXISTS marks (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id        BIGINT NOT NULL,
    subject_name      VARCHAR(200) NOT NULL,
    internal_marks    DECIMAL(5,2) DEFAULT 0.00 COMMENT 'Out of 40',
    external_marks    DECIMAL(5,2) DEFAULT 0.00 COMMENT 'Out of 60',
    total_marks       DECIMAL(5,2) GENERATED ALWAYS AS (internal_marks + external_marks) STORED,
    grade             ENUM('A+', 'A', 'B', 'C', 'D', 'F') DEFAULT 'F',
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE 5: attendance (Class attendance logs)
-- ============================================================
CREATE TABLE IF NOT EXISTS attendance (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id              BIGINT NOT NULL,
    subject_name            VARCHAR(200) NOT NULL,
    total_classes           INT DEFAULT 0,
    attended_classes        INT DEFAULT 0,
    attendance_percentage   DECIMAL(5,2) GENERATED ALWAYS AS (
        CASE
            WHEN total_classes > 0
            THEN ROUND((attended_classes / total_classes) * 100, 2)
            ELSE 0
        END
    ) STORED,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE 6: performance_analysis (Student overall analysis 1:1)
-- ============================================================
CREATE TABLE IF NOT EXISTS performance_analysis (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id      BIGINT NOT NULL UNIQUE,
    overall_score   DECIMAL(5,2) DEFAULT 0.00,
    risk_level      ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'LOW',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ============================================================
-- TABLE 7: result_prediction (Student result prediction 1:1)
-- ============================================================
CREATE TABLE IF NOT EXISTS result_prediction (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id          BIGINT NOT NULL UNIQUE,
    predicted_grade     VARCHAR(5),
    confidence_score    DECIMAL(5,2) DEFAULT 0.00,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ============================================================
-- INDEXES for performance optimization
-- ============================================================
CREATE INDEX IF NOT EXISTS idx_students_department ON students(department);
CREATE INDEX IF NOT EXISTS idx_students_semester ON students(semester);
CREATE INDEX IF NOT EXISTS idx_marks_student ON marks(student_id);
CREATE INDEX IF NOT EXISTS idx_attendance_student ON attendance(student_id);
