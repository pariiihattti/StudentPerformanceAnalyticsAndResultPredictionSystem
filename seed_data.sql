-- ============================================================
-- Dummy Seed Data for Student Performance Analytics System
-- Run AFTER schema.sql
-- ============================================================

USE student_performance_db;

-- ============================================================
-- Admin
-- ============================================================
INSERT INTO admin (username, password, email) VALUES
  ('admin', 'admin123', 'admin@university.edu')
ON DUPLICATE KEY UPDATE username=username;

-- ============================================================
-- Teacher
-- ============================================================
INSERT INTO teacher (admin_id, username, password, full_name, email) VALUES
  (1, 'teacher', 'teacher123', 'Prof. Robert Smith', 'robert.smith@university.edu')
ON DUPLICATE KEY UPDATE username=username;

-- ============================================================
-- Students (10 dummy students across departments)
-- ============================================================
INSERT INTO students (admin_id, roll_number, name, email, department, semester, batch_year, password) VALUES
  (1, 'CS202401', 'Aarav Sharma',  'aarav.sharma@example.com',  'Computer Science',      6, '2024', 'student123'),
  (1, 'CS202402', 'Priya Patel',   'priya.patel@example.com',   'Computer Science',      6, '2024', 'student123'),
  (1, 'IT202401', 'Rohan Verma',   'rohan.verma@example.com',   'Information Technology',4, '2024', 'student123'),
  (1, 'IT202402', 'Ananya Sen',    'ananya.sen@example.com',    'Information Technology',4, '2024', 'student123'),
  (1, 'EC202401', 'Vikram Singh',  'vikram.singh@example.com',  'Electronics & Comm',    6, '2024', 'student123'),
  (1, 'CS202403', 'Neha Gupta',    'neha.gupta@example.com',    'Computer Science',      6, '2024', 'student123'),
  (1, 'ME202401', 'Kabir Mehta',   'kabir.mehta@example.com',   'Mechanical Eng',        2, '2024', 'student123'),
  (1, 'CE202401', 'Diya Roy',      'diya.roy@example.com',      'Civil Eng',             2, '2024', 'student123'),
  (1, 'CS202404', 'Arjun Reddy',   'arjun.reddy@example.com',   'Computer Science',      8, '2024', 'student123'),
  (1, 'IT202403', 'Kavya Nair',    'kavya.nair@example.com',    'Information Technology',8, '2024', 'student123')
ON DUPLICATE KEY UPDATE password=VALUES(password);

-- ============================================================
-- Marks (Subject-wise internal + external scores)
-- ============================================================
-- NOTE: total_marks and grade are auto-calculated in schema,
--       but inserted manually here for compatibility.
INSERT INTO marks (student_id, subject_name, internal_marks, external_marks, grade) VALUES
  -- Aarav Sharma (CS) - High performer
  (1, 'Data Structures',       36, 52, 'A'),
  (1, 'Database Systems',      35, 55, 'A+'),
  (1, 'Web Development',       38, 56, 'A+'),

  -- Priya Patel (CS) - Average
  (2, 'Data Structures',       25, 38, 'C'),
  (2, 'Database Systems',      28, 40, 'B'),

  -- Rohan Verma (IT) - At Risk (low scores + low attendance)
  (3, 'Java Programming',      15, 22, 'F'),
  (3, 'Computer Networks',     18, 25, 'F'),

  -- Ananya Sen (IT) - Good performer
  (4, 'Java Programming',      32, 48, 'A'),
  (4, 'Computer Networks',     34, 50, 'A'),

  -- Vikram Singh (EC) - Average
  (5, 'Digital Electronics',   30, 42, 'B'),
  (5, 'Microprocessors',       28, 44, 'B'),

  -- Neha Gupta (CS) - Top performer
  (6, 'Data Structures',       39, 58, 'A+'),
  (6, 'Database Systems',      38, 57, 'A+'),

  -- Kabir Mehta (ME) - Average
  (7, 'Thermodynamics',        24, 36, 'C'),

  -- Diya Roy (CE) - Good
  (8, 'Structural Engineering',31, 49, 'A'),

  -- Arjun Reddy (CS) - Final year, high scorer
  (9, 'Artificial Intelligence',37, 55, 'A+'),

  -- Kavya Nair (IT) - Final year, good
  (10,'Cloud Computing',       33, 47, 'A');

-- ============================================================
-- Attendance Logs (subject-wise class attendance)
-- ============================================================
INSERT INTO attendance (student_id, subject_name, total_classes, attended_classes) VALUES
  -- Aarav Sharma - Good attendance
  (1, 'Data Structures',       40, 38),
  (1, 'Database Systems',      40, 36),

  -- Priya Patel - Borderline 75%
  (2, 'Data Structures',       40, 30),
  (2, 'Database Systems',      40, 31),

  -- Rohan Verma - VERY LOW attendance (at risk)
  (3, 'Java Programming',      40, 20),
  (3, 'Computer Networks',     40, 22),

  -- Ananya Sen - Good attendance
  (4, 'Java Programming',      40, 36),
  (4, 'Computer Networks',     40, 37),

  -- Vikram Singh - Good attendance
  (5, 'Digital Electronics',   40, 34),

  -- Neha Gupta - Excellent attendance
  (6, 'Data Structures',       40, 39),
  (6, 'Database Systems',      40, 40),

  -- Kabir Mehta
  (7, 'Thermodynamics',        40, 32),

  -- Diya Roy
  (8, 'Structural Engineering',40, 38),

  -- Arjun Reddy
  (9, 'Artificial Intelligence',40, 39),

  -- Kavya Nair
  (10,'Cloud Computing',       40, 35);

-- ============================================================
-- Performance Analysis Data (1:1 with student)
-- ============================================================
INSERT INTO performance_analysis (student_id, overall_score, risk_level) VALUES
  (1, 92.5, 'LOW'),
  (2, 65.0, 'MEDIUM'),
  (3, 42.5, 'HIGH'),
  (4, 85.0, 'LOW'),
  (5, 72.5, 'MEDIUM'),
  (6, 96.0, 'LOW'),
  (7, 60.0, 'MEDIUM'),
  (8, 80.0, 'LOW'),
  (9, 92.0, 'LOW'),
  (10, 80.0, 'LOW');

-- ============================================================
-- Result Prediction Data (1:1 with student)
-- ============================================================
INSERT INTO result_prediction (student_id, predicted_grade, confidence_score) VALUES
  (1, 'A+', 95.0),
  (2, 'C', 70.0),
  (3, 'F', 85.0),
  (4, 'A', 88.0),
  (5, 'B', 75.0),
  (6, 'A+', 98.0),
  (7, 'C', 65.0),
  (8, 'B+', 80.0),
  (9, 'A+', 90.0),
  (10, 'A', 85.0);
