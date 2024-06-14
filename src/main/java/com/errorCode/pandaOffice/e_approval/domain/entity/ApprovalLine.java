package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "approval_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 기안된 결재 서류의 결재선 */
public class ApprovalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 서류의 ID */
    @Column(nullable = false)
    private int documentId;
    /* 결재 순서 */
    @Column(nullable = false, name = "`order`")
    private int order;
    /* 순서에 맞는 사원 */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    /* 결재 상태 */
    @Column(nullable = false)
    private ApproveType status;
}
