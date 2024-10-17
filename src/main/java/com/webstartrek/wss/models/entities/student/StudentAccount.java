package com.webstartrek.wss.models.entities.student;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "student_accounts")
@Data
@Builder
public class StudentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_accounts_sequence")
    @SequenceGenerator(name = "student_accounts_sequence", sequenceName = "student_accounts_id_sequence",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private long createdAt;

    @Column(nullable = false)
    private long lastUpdateAt;

    @Version
    private int version;

    @Column(nullable = false)
    private String lastActionBy;
}