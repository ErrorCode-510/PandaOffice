package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalLine.CreateApprovalLineRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CreateApprovalDocumentRequest {
    private String title;
    private int documentTemplateId;
    private Integer employeeId;
    private String document;
    private List<CreateApprovalLineRequest> approvalLineList;
}
