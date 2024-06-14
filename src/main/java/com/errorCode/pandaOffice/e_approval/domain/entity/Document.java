package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.domain.type.ApproveState;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "approval_document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 결재 서류*/
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 결재서 제목 */
    private String title;
    /* 결재 상태 */
    private int approvalStatus;
    @ManyToOne
    @JoinColumn(nullable = false, name="draft_employee_id")
    /* 기안자 */
    private Employee draftEmployee;
    /* 기안일 */
    private Date approvalDate;
    /* 부서 ID */
    private int departmentId;
    /* 문서 파일 */
    private String document;
    /* 결재 상태 */
    private ApproveState state;
    /* 문서 첨부파일 리스트 */
    @OneToMany
    @JoinColumn(name = "documentId")
    private List<DocumentAttachedFile> attachments;
}
