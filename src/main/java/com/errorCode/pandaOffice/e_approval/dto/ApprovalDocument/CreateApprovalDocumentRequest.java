package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalLine.CreateApprovalLineRequest;
import jakarta.validation.constraints.NotBlank;
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
    private final List<CreateApprovalLineRequest> approvalLineList;
}
