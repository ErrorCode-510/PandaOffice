package com.errorCode.pandaOffice.e_approval.dto.approvalDocument;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class CreateApprovalDocumentRequest {
    private final String title;
    private final int documentTemplateId;
    private final String document;
    private final List<ApprovalLineRequest> approvalLineList;

    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class ApprovalLineRequest {
        private final int order;
        private final int employeeId;
    }
}
