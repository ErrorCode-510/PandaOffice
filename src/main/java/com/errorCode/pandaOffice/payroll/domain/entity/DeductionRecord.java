package com.errorCode.pandaOffice.payroll.domain.entity;

import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
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
    private int deductionId;

    /* 공제 항목 코드 */
    @ManyToOne
    @JoinColumn(name = "deduction_category_id")
    private DeductionCategory deductionCategory;

    /* 금액 */
    @Column(name = "amount")
    private int amount;

    public static DeductionRecord of(PayrollRequest.DeductionRequest request, DeductionCategory categoryEntity) {
        DeductionRecord newRecord = new DeductionRecord();
        newRecord.deductionCategory = categoryEntity;
        newRecord.amount = request.getAmount();
        return newRecord;
    }
}
