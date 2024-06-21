package com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalLine;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateApprovalLineRequest {
    private int order;
    private Integer employeeId;
    private Integer departmentId;
    private Integer jobId;
}
