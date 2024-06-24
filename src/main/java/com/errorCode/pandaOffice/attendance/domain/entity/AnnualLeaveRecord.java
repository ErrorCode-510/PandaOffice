package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
/* 연차 기록 */
public class AnnualLeaveRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 연차 기록 코드 */
    private int id;

    /* 연차 기록 변동일 */
    private LocalDate date;

    /* 잔여 연차 */
    private double nowAmount;

    /* 수량 (연차를 얼마나 썼는지, 받았는지에 대한 수량임) */
    private double amount;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "annual_leave_category_id")
    /* 연차 분류 코드 */
    private AnnualLeaveCategory annualLeaveCategory;

    @ManyToOne
    @JoinColumn(name = "approval_document_id")
    /* 신청 서류 코드 */
    private ApprovalDocument approvalDocument;

    public AnnualLeaveRecord(int id, LocalDate date, double nowAmount, double amount,
                             Employee employee, AnnualLeaveCategory annualLeaveCategory,
                             ApprovalDocument approvalDocument) {
        this.id = id;
        this.date = date;
        this.nowAmount = nowAmount;
        this.amount = amount;
        this.employee = employee;
        this.annualLeaveCategory = annualLeaveCategory;
        this.approvalDocument = approvalDocument;
    }
}
