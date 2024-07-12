package com.errorCode.pandaOffice.e_approval.dto.approvalDocument.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateApprovalDocumentRequest {
    @NotBlank
    private final int documentId;
    private final int order;
    @Min(value = 2)
    @Max(value = 8)
    private final int type;
    private final LocalDate date = LocalDate.now();
    private final String comment;
}
