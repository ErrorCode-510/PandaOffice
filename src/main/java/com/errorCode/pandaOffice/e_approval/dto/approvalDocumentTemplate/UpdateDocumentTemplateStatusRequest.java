package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateDocumentTemplateStatusRequest {
    private final int folderId;
    private final boolean status;
    private final List<Integer> documentIdList;
}
