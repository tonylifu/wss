package com.webstartrek.wss.models.entities.student;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "student_numbers")
@Data
@Builder
public class StudentNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_numbers_sequence")
    @SequenceGenerator(name = "student_numbers_sequence", sequenceName = "student_numbers_id_sequence",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private int currentYear;

    @Column(nullable = false)
    private int studentNumber;

    @Column(nullable = false)
    private long createdAt;

    @Column(nullable = false)
    private String actionBy;
}