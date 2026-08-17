package com.studentperformance.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    private String subjectName;

    private double internalMarks; // Out of 40

    private double externalMarks; // Out of 60

    private double totalMarks;    // Out of 100

    private String grade;         // A+, A, B, C, D, F
}
