package com.studentperformance.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "performance_analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Student student;

    @Column(name = "overall_score")
    private Double overallScore;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}
