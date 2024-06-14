package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
/* 연장 근무 기록 */
public class OvertimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 연장 근무 코드 */
    private int id;

    /* 연장 근무 시작일 */
    private LocalTime startDate;

    /* 연장 근무 종료일 */
    private LocalTime endDate;

    /* 연장 근무 타입 */
    private String type;

    @ManyToOne
    @JoinColumn(name = "approval_document_id")
    /* 신청 서류 코드 */
    private ApprovalDocument approvalDocument;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;

    public OvertimeRecord(int id, LocalTime startDate, LocalTime endDate, String type) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }
}
