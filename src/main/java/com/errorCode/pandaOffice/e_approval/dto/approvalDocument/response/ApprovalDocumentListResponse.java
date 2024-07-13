package com.errorCode.pandaOffice.e_approval.dto.approvalDocument.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovalDocumentListResponse {
    private final int id;
    private final String title;
    private final String templateName;
    private final String draftEmployeeName;
    private final LocalDate approvalDate;
    private final LocalDate lastApprovalDate;
    private final String departmentName;
    private final String status;

    public static ApprovalDocumentListResponse from(final ApprovalDocument approvalDocument){
        return new ApprovalDocumentListResponse(
                approvalDocument.getId(),
                approvalDocument.getTitle(),
                approvalDocument.getDocumentTemplate().getTitle(),
                approvalDocument.getDraftEmployee().getName(),
                approvalDocument.getDraftDate(),
                approvalDocument.getLastApprovalDate(),
                approvalDocument.getDepartment().getName(),
                approvalDocument.getStatus().getDescription()
        );
    }
}
