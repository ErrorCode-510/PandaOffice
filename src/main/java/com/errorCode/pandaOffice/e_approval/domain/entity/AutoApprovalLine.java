package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request.CreateApprovalDocumentTemplateRequest;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request.UpdateApprovalDocumentTemplateRequest;
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

    /* 결재 순서 */
    @Column(nullable = false, name = "`order`")
    private int order;

    /* 사원 명시 */
    private Integer employeeId;

    /* 직급, 부서 명시 */
    private Integer jobId;
    private Integer departmentId;


    public static AutoApprovalLine of(CreateApprovalDocumentTemplateRequest.AutoApprovalLineRequest line) {
        AutoApprovalLine lineEntity = new AutoApprovalLine();
        lineEntity.order = line.getOrder();
        lineEntity.employeeId = line.getEmployeeId();
        lineEntity.jobId = line.getJobId();
        lineEntity.departmentId = line.getDepartmentId();
        return lineEntity;
    }

    public static AutoApprovalLine of(UpdateApprovalDocumentTemplateRequest.AutoApprovalLine line) {
        AutoApprovalLine lineEntity = new AutoApprovalLine();
        lineEntity.order = line.getOrder();
        lineEntity.employeeId = line.getEmployeeId();
        lineEntity.jobId = line.getJobId();
        lineEntity.departmentId = line.getDepartmentId();
        return lineEntity;
    }
}
