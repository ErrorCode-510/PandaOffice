package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
@RequiredArgsConstructor
public class UpdateApprovalDocumentTemplateRequest {
    private final int id;
    private final String title;
    private final String document;
    private final boolean status;
    private final int folderId;
    private final List<AutoApprovalLine> newApprovalLine;

    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class AutoApprovalLine{
        private final int order;
        private final Integer employeeId;
        private final Integer jobId;
        private final Integer departmentId;
    }
}
