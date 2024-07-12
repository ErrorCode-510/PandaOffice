package com.errorCode.pandaOffice.e_approval.dto.approvalDocument.response;

import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.e_approval.dto.EmployeeResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.ApprovalDocumentTemplateResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class ApprovalDocumentDetailResponse {
    private int id;
    private EmployeeResponse currentEmployee;
    private EmployeeResponse draftEmployee;
    private String name;
    private String templateName;
    private String title;
    private String document;
    private ApprovalStatus status;
    private LocalDate draftDate;
    private List<ApprovalLineResponse> approvalLineList;

    @Setter
    @Getter
    @Builder
    public static class ApprovalLineResponse{
        private int order;
        private EmployeeResponse employee;
        private String comment;
        private LocalDate handlingDate;
        private ApproveTypeMap status;
        private List<ApproveTypeMap> approvalAbleList;
    }
    @Setter
    @Getter
    @Builder
    public static class ApproveTypeMap{
        private int value;
        private String name;
    }
}
