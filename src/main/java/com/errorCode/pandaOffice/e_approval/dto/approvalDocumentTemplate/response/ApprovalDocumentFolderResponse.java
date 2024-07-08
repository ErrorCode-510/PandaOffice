package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplateFolder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class ApprovalDocumentFolderResponse {
    int folderId;
    String name;
    List<DocumentTemplateResponse> documentList;
    Integer refFolderId;

    public static ApprovalDocumentFolderResponse of(DocumentTemplateFolder folderEntity, List<DocumentTemplate> templates) {
        return ApprovalDocumentFolderResponse.builder()
                .folderId(folderEntity.getId())
                .name(folderEntity.getName())
                .documentList(templates
                        .stream()
                        .map(entity->DocumentTemplateResponse.of(entity))
                        .toList())
                .refFolderId(folderEntity.getRefFolderId())
                .build();
    }

    @Setter
    @Getter
    @ToString
    @Builder
    public static class DocumentTemplateResponse {
        int documentId;
        String title;
        String description;
        String lastEditorName;
        String lastEditorJob;
        LocalDate lastEditDate;
        String status;

        public static DocumentTemplateResponse of(DocumentTemplate entity) {
            return DocumentTemplateResponse.builder()
                    .documentId(entity.getId())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .lastEditorName(entity.getLastEditor().getName())
                    .lastEditorJob(entity.getLastEditor().getJob().getTitle())
                    .lastEditDate(entity.getLastEditDate())
                    .status(entity.isStatus()?"사용":"미사용")
                    .build();
        }
    }
}
