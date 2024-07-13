package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class CreateApprovalDocumentTemplateRequest {
    private final String name;
    private final String lastEditComment;
    private final String title;
    private final String description;
    private final String document;
    private final List<AutoApprovalLineRequest> autoApprovalLineRequestList;
    private final Integer folderId;
    private final boolean status;
    private final Integer integrateFeatureId;


    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class AutoApprovalLineRequest{
        private final int order;
        private final Integer employeeId;
        private final Integer jobId;
        private final Integer departmentId;
    }
}
