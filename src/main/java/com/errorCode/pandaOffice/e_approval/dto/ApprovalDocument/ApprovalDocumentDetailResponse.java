package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalDocumentDetailResponse {
    private int id;
    private String title;
    private String documentTemplate;

    public static ApprovalDocumentDetailResponse of(ApprovalDocument document) {
        ApprovalDocumentDetailResponse response = new ApprovalDocumentDetailResponse();
        response.setId(document.getId());
        response.setTitle(document.getTitle());
        response.setDocumentTemplate(document.getDocumentTemplate().getDocument());
        return response;
    }
}
