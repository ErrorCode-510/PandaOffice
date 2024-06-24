package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalLine.CreateApprovalLineRequest;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "approval_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
/* 기안된 결재 서류의 결재선 */
public class ApprovalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 결재 순서 */
    @Column(nullable = false, name = "`order`")
    private int order;
    /* 처리 일자 */
    private LocalDate handlingDate = null;
    /* 순서에 맞는 사원 */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    /* 결재 상태 */
    @Column(nullable = false)
    private ApproveType status = ApproveType.SCHEDULED;
    private String comment;


    public static ApprovalLine of(CreateApprovalLineRequest request, Employee employee) {
        ApprovalLine approvalLine = new ApprovalLine();
        approvalLine.order = request.getOrder();
        approvalLine.employee = employee;
        if(request.getOrder() == 1){
            approvalLine.status = ApproveType.PENDING;
        }
        return approvalLine;
    }

    public void processApproval(ApproveType type, String comment) {
        this.status = type;
        this.comment = comment == null ? type.getDescription() + "처리 되었습니다." : comment;
    }

    public void changeEmployee(Employee currentEmployee) {
        this.employee = currentEmployee;
    }

    public void changeStatus(ApproveType approveType) {
        this.status = approveType;
    }
}
