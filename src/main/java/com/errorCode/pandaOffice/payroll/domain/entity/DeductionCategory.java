package com.errorCode.pandaOffice.payroll.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 공제 항목 Entity */
@Entity
@Table(name = "deduction_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeductionCategory {

    /* 공제 항목 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int deductionCategoryId;

    /* 항목명 */
    @Column(name = "name")
    private String name;

    /* 공제율 */
    @Column(name = "deduction_rate")
    private double deductionRate;
}
