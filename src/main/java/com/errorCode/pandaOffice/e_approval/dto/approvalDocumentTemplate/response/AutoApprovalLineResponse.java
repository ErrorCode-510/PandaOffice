package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.AutoApprovalLine;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AutoApprovalLineResponse {
    private int order;
    private Integer employeeId;
    private Integer jobId;
    private Integer departmentId;
}
