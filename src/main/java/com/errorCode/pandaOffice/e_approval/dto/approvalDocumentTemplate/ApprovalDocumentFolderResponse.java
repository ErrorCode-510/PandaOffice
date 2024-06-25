package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate;

import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplateFolder;
import com.errorCode.pandaOffice.e_approval.domain.repository.ApprovalDocumentRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class ApprovalDocumentFolderResponse {
    int folderId;
    String name;
    List<DocumentTemplateResponse> documentList;
    List<ApprovalDocumentFolderResponse> subFolderList;

    @Setter
    @Getter
    @ToString
    public static class DocumentTemplateResponse {
        int documentId;
        String title;


        public static DocumentTemplateResponse of(DocumentTemplate documentEntity) {
            DocumentTemplateResponse template = new DocumentTemplateResponse();
            template.documentId = documentEntity.getId();
            template.title = documentEntity.getTitle();
            return template;
        }
    }

    public static ApprovalDocumentFolderResponse of(DocumentTemplateFolder folderEntity) {
        ApprovalDocumentFolderResponse response = new ApprovalDocumentFolderResponse();
        response.folderId = folderEntity.getId();
        response.name = folderEntity.getName();
        response.documentList = new ArrayList<>();
        response.subFolderList = new ArrayList<>();
        return response;
    }
}
