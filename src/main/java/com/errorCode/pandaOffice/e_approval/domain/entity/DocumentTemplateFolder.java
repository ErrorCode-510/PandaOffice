package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request.CreateApprovalDocumentFolderRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_document_template_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 양식 저장 폴더 */
public class DocumentTemplateFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 폴더 이름 */
    @Column(nullable = false)
    private String name;
    /* 상위 폴더 ID */
    @Column(nullable = true)
    private Integer refFolderId;

    public static DocumentTemplateFolder of(CreateApprovalDocumentFolderRequest request) {
        DocumentTemplateFolder folder = new DocumentTemplateFolder();
        folder.name = request.getName();
        folder.refFolderId = request.getRefFolderId();
        return folder;
    }

    public void modifyName(String newName) {
        this.name = newName;
    }
}
