package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auto_approval_line_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 자동 결재선의 순서 */
public class AutoApprovalLineOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 자동 결재선 ID */
    private int autoApprovalId;
    /* 결재 순서 */
    @Column(nullable = false, name = "`order`")
    private int order;

    /* 사원 명시 */
    private int employeeId;

    /* 직급, 부서 명시 */
    private int jobId;
    private int departmentId;
}
