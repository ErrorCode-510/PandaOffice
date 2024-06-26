package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department_document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 부서에 저장되는 결재서류 */
public class DepartmentDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 문서 양식 ID */
    @ManyToOne
    @JoinColumn(name = "document_template_id")
    private ApprovalDocument document;
}
