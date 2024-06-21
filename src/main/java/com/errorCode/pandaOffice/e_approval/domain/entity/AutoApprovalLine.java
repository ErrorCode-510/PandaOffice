package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "auto_approval_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class AutoApprovalLine {
    /* 양식에 맞는 결재선 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 문서 양식 ID*/
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
