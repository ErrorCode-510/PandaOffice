package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auto_approval_line_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 자동 결재선의 폴더 */
public class AutoApprovalLineFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 결재선 폴더명 */
    @Column(nullable = false)
    private String name;
    /* 상위 폴더 */
    private int refFolderId;
}
