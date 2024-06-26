package com.errorCode.pandaOffice.e_approval.dto.departmentBox.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateDepartmentBoxRequest {
    private final int departmentBoxId;
    private final String name;
    private final List<Document> documentList;

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class Document{
        private final int documentId;
    }}
