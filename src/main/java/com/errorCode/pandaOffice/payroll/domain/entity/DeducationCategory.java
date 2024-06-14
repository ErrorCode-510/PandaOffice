package com.errorCode.pandaOffice.payroll.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/* 공제 항목 Entity */
@Entity
@Table(name = "deducation_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeducationCategory {

    /* 공제 항목 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /* 항목명 */
    @Column(name = "name", nullable = false)
    private String name;

    /* 공제율 */
    @Column(name = "deducation_rate", nullable = false)
    private double deducationRate;
}
