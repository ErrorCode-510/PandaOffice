package com.errorCode.pandaOffice.payroll.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/* 공제 기록 Entity */
@Entity
@Table(name = "deduction_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DeductionRecord {

    /* 공제 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /* 급여 기록 코드 */
    @ManyToOne
    @JoinColumn(name = "payroll_record_id", nullable = false)
    private PayrollRecord payrollRecordId;

    /* 공제 항목 코드 */
    @ManyToOne
    @JoinColumn(name = "deducation_category_id", nullable = false)
    private DeducationCategory deducationCategory;

    /* 금액 */
    @Column(name = "amount")
    private int amount;
}
