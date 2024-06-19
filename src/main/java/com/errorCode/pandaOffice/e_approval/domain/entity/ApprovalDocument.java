package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.type.ApproveState;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "approval_document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
/* 전자결재 */
public class ApprovalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 결재서 제목 */
    private String title;
    @ManyToOne
    @JoinColumn(nullable = false, name = "document_template_id")
    private DocumentTemplate documentTemplate;
    @ManyToOne
//    @JoinColumn(nullable = false, name="draft_employee_id")
    @JoinColumn(nullable = false, name="draft_employee_id")
    /* 기안자 */
    private Employee draftEmployee;
    /* 기안일 */
    private LocalDate approvalDate;
    /* 최종 결재일 */
    private LocalDate lastApprovalDate;
    /* 부서 ID */
    @ManyToOne
    @JoinColumn(nullable = false, name = "departmentId")
    private Department department;
    /* 문서 파일 */
    private String document;
    /* 결재 상태 */
    @Enumerated(EnumType.STRING)
    private ApproveState state;
    /* 문서 첨부파일 리스트 */
    @OneToMany
    @JoinColumn(name = "documentId")
    private List<DocumentAttachedFile> attachments;
}
