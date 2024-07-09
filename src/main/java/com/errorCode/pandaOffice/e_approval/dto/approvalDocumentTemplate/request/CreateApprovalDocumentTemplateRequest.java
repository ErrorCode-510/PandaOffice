package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CreateApprovalDocumentTemplateRequest {
    private final String name;
    private final String lastEditComment;
    private final String title;
    private final String description;
    private final String document;
    private final List<AutoApprovalLineRequest> autoApprovalLineRequestList;
    private final Integer folderId = 1;


    @RequiredArgsConstructor
    @Getter
    public static class AutoApprovalLineRequest{
        private final int order;
        private final boolean allApprovalAble;
        private final Integer employeeId;
        private final Integer jobId;
        private final Integer departmentId;
    }
}
