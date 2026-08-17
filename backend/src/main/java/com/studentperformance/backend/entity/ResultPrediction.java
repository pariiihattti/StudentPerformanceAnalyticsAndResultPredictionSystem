package com.studentperformance.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "result_prediction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Student student;

    @Column(name = "predicted_grade")
    private String predictedGrade;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}
