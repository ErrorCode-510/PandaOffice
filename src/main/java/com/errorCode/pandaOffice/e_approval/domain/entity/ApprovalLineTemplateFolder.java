package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_line_template_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 결재선 템플릿의 폴더 */
public class ApprovalLineTemplateFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 결재선 폴더명 */
    @Column(nullable = false)
    private String name;
    /* 상위 폴더 */
    private int refFolderId;}
