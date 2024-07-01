package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AnnualLeaveUsedRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 소진 연차 기록 코드 */
    private int id;

    /* 연차 사용 시작일 */
    private LocalDate usedStartDate;

    /* 연차 사용 종료일 */
    private LocalDate usedEndDate;

    /* 연차 사용 시간대(오전, 오후, 전체) */
    private String leaveSession;

    /* 연차 사용량 */
    private double usedAmount;

    /* 잔여 연차량 */
    private double remainingAmount;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "annual_leave_category_id")
    /* 연차 분류 코드 */
    private AnnualLeaveCategory annualLeaveCategory;

    @ManyToOne
    @JoinColumn(name="annual_leave_grant_record_id")
    /* 연차 부여 코드 */
    private AnnualLeaveGrantRecord annualLeaveGrantRecord;

    @ManyToOne
    @JoinColumn(name = "approval_document_id")
    /* 신청 서류 코드 */
    private ApprovalDocument approvalDocument;

}
