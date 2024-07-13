package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateApprovalDocumentFolderRequest {
    private String name;
    private Integer refFolderId;
}
