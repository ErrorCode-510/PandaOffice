package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "approval_line_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 결재선 템플릿 */
public class ApprovalLineTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 자동 결재선 이름 */
    @Column(nullable = false)
    private String name;
    /* 최종 수정자 */
    @ManyToOne
    @JoinColumn(name="last_editor_id")
    private Employee lastEditor;
    /* 폴더 ID */
    @Column(nullable = false)
    private int folderId;
    /* 결재선 리스트 */
    @OneToMany
    @JoinColumn(name="approval_line_template_id")
    private List<ApprovalLineTemplateOrder> orderLine;
}
