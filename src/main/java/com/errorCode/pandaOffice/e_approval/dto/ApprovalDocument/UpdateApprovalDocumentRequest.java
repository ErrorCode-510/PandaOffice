package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class UpdateApprovalDocumentRequest {
    private int documentId;
    private int approvalLineId;
    private int Status;
    private LocalDate date = LocalDate.now();
    private String comment;
}
