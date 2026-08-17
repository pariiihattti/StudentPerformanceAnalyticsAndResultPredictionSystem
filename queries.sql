-- ============================================================
-- Useful SQL Queries for Student Performance Analytics System
-- ============================================================

USE student_performance_db;

-- 1. View all students with their overall average marks
SELECT
    s.roll_number,
    s.name,
    s.department,
    s.semester,
    ROUND(AVG(m.total_marks), 2) AS avg_score,
    ROUND(AVG(a.attendance_percentage), 2) AS avg_attendance
FROM students s
LEFT JOIN marks m ON s.id = m.student_id
LEFT JOIN attendance a ON s.id = a.student_id
GROUP BY s.id, s.roll_number, s.name, s.department, s.semester
ORDER BY avg_score DESC;

-- 2. Find at-risk students (marks < 50 or attendance < 75%)
SELECT
    s.roll_number,
    s.name,
    s.department,
    ROUND(AVG(m.total_marks), 2) AS avg_marks,
    ROUND(AVG(a.attendance_percentage), 2) AS avg_attendance,
    'HIGH RISK' AS risk_status
FROM students s
LEFT JOIN marks m ON s.id = m.student_id
LEFT JOIN attendance a ON s.id = a.student_id
GROUP BY s.id, s.roll_number, s.name, s.department
HAVING avg_marks < 50 OR avg_attendance < 75;

-- 3. Grade distribution across all students
SELECT
    m.grade,
    COUNT(*) AS count
FROM marks m
GROUP BY m.grade
ORDER BY m.grade;

-- 4. Department-wise average performance
SELECT
    s.department,
    ROUND(AVG(m.total_marks), 2) AS avg_marks,
    ROUND(AVG(a.attendance_percentage), 2) AS avg_attendance,
    COUNT(DISTINCT s.id) AS student_count
FROM students s
LEFT JOIN marks m ON s.id = m.student_id
LEFT JOIN attendance a ON s.id = a.student_id
GROUP BY s.department
ORDER BY avg_marks DESC;

-- 5. Students with low attendance (below 75%)
SELECT
    s.name,
    s.roll_number,
    a.subject_name,
    a.attended_classes,
    a.total_classes,
    a.attendance_percentage
FROM attendance a
JOIN students s ON s.id = a.student_id
WHERE a.attendance_percentage < 75
ORDER BY a.attendance_percentage ASC;

-- 6. Top performing students (avg >= 80)
SELECT
    s.roll_number,
    s.name,
    s.department,
    ROUND(AVG(m.total_marks), 2) AS avg_score
FROM students s
JOIN marks m ON s.id = m.student_id
GROUP BY s.id, s.roll_number, s.name, s.department
HAVING avg_score >= 80
ORDER BY avg_score DESC;
