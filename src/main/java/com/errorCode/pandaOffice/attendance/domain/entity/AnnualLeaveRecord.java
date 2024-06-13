package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Date date;

    /* 잔여 연차 */
    private double nowAmount;

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




}
