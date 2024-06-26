package com.errorCode.pandaOffice.e_approval.dto.departmentBox.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.DepartmentBox;
import com.errorCode.pandaOffice.e_approval.domain.entity.DepartmentDocument;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class DepartmentBoxResponse {
    private int departmentBoxId;
    private int order;
    private String name;
    private EditData editData;
    private List<DepartmentDocumentResponse> documentList;

    public static DepartmentBoxResponse of(DepartmentBox entity) {
        DepartmentBoxResponse response = new DepartmentBoxResponse();

        response.departmentBoxId = entity.getId();
        response.order = entity.getOrder();
        response.name = entity.getName();
        response.editData = EditData.of(entity);
        response.documentList = entity.getDocumentList().stream().map(
                departmentBoxEntity->DepartmentDocumentResponse.of(departmentBoxEntity.getDocument())
        ).toList();

        return response;
    }

    @Getter
    @ToString
    public static class EditData {
        private int employeeId;
        private String name;
        private String departmentName;
        private String jobTitle;
        private LocalDate lastEditDate;

        public static EditData of(DepartmentBox entity) {
            EditData data = new EditData();

            data.name = entity.getLastEditor().getName();
            data.employeeId = entity.getLastEditor().getEmployeeId();
            data.departmentName = entity.getDepartment().getName();
            data.jobTitle = entity.getLastEditor().getJob().getTitle();
            data.lastEditDate = entity.getLastEditDate();
            return data;
        }
    }
    @Getter
    @ToString
    public static class DepartmentDocumentResponse{
        private int documentId;
        private String documentName;

        public static DepartmentDocumentResponse of(ApprovalDocument document) {
            DepartmentDocumentResponse response = new DepartmentDocumentResponse();
            response.documentId = document.getId();
            response.documentName = document.getTitle();
            return response;
        }
    }
}
