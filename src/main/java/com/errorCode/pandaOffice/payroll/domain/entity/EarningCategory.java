package com.errorCode.pandaOffice.payroll.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 지급 항목 Entity */
@Entity
@Table(name = "earning_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EarningCategory {

    /* 지급 항목 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int earningCategoryId;

    /* 급여사항명 */
    @Column(name = "name")
    private String name;

    /* 과세여부 */
    @Column(name = "is_tax")
    private String isTax;

}
