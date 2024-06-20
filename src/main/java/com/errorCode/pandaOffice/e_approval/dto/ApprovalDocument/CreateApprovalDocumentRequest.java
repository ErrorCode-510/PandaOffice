package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
public class CreateApprovalDocumentRequest {
    private int id;
    private String title;
    private int documentTemplateId;
    private int employeeId;
    private LocalDate approvalDate = LocalDate.now();
    private LocalDate lastApprovalDate = null;
    private int departmentId;
    private String document;
    private int status = 1;
    private List<Integer> approvalDocumentList;
}
