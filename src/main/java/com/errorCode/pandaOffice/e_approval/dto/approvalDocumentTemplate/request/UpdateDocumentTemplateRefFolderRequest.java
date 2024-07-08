package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class UpdateDocumentTemplateRefFolderRequest {
    private final int beforeFolderId;
    private final int afterFolderId;
    private final List<Integer> templateIdList;
}
