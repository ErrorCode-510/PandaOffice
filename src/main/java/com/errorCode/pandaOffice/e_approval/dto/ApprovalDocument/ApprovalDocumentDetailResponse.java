package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ApprovalDocumentDetailResponse {
    private int id;
    private String title;
    private String documentTemplate;
    List<ApprovalLineResponse> approvalLines;

    public static ApprovalDocumentDetailResponse of(ApprovalDocument document) {
        ApprovalDocumentDetailResponse response = new ApprovalDocumentDetailResponse();
        response.setId(document.getId());
        response.setTitle(document.getTitle());
        response.setDocumentTemplate(document.getDocumentTemplate().getDocument());
        /* 결재선 할당 과정 */
        response.setApprovalLines(document.getApprovalLineList().stream().map(
                approvalLine -> new ApprovalLineResponse(
                        approvalLine.getOrder(),
                        approvalLine.getHandlingDate(),
                        approvalLine.getStatus().getDescription())
        ).toList());
        return response;
    }
    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    private static class ApprovalLineResponse {
        private int order;
        private LocalDate handlingDate;
        private String status;
    }
}
