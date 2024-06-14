package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_document_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 결재 문서 양식 */
public class DocumentTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 문서 양식 이름 */
    @Column(nullable = false)
    private String title;
    /* 문서 양식 */
    @Column(nullable = false)
    private String document;
    /* 사용 상태 */
    @Column(nullable = false)
    private boolean status;
    /* 최종 수정자 */
    @ManyToOne
    @JoinColumn(name = "last_editor_id", nullable = false)
    private Employee lastEditor;
    /* 자동 결재선 ID */
    @ManyToOne
    @JoinColumn(name = "auto_approval_line_id")
    private AutoApprovalLine autoApprovalLine;
    /* 수정 가능 여부 */
    @Column(nullable = false)
    private boolean editAbleStatus;
    /* 양식 상위 폴더 */
    @Column(nullable = false)
    private int folderId;
}
