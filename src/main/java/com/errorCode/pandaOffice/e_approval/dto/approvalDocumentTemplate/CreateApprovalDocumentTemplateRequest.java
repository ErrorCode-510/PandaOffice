package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CreateApprovalDocumentTemplateRequest {
    private final String title;
    private final String document;
    private final List<AutoApprovalLineRequest> autoApprovalLineRequestList;
    private final Integer folderId;


    @RequiredArgsConstructor
    @Getter
    public static class AutoApprovalLineRequest{
        private final int order;
        private final Integer employeeId;
        private final Integer jobId;
        private final Integer departmentId;
    }
}
