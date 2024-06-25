package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLineTemplateOrder;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class ApprovalDocumentTemplateResponse {

    /* 템플릿 ID */
    private int id;
    /* 템플릿 이름 */
    private String title;
    /* 문서 내용 */
    private String document;
    /* 사용 상태 */
    private boolean status;
    /* 생성된 결재선의 리스트 */
    private List<AutoApprovalLine> autoApprovalLine;

    @ToString
    @Getter
    public static class AutoApprovalLine {
        /* 사번 */
        private int employeeId;
        /* 이름 */
        private String employeeName;
        /* 부서명 */
        private String departmentName;
        /* 직급명 */
        private String jobTitle;
        /* 순서 */
        private int order;

        public static List<AutoApprovalLine> of(Map<Integer, Employee> lineMap) {
            return lineMap.entrySet().stream().map(entry -> {
                AutoApprovalLine line = new AutoApprovalLine();
                line.employeeId = entry.getValue().getEmployeeId();
                line.employeeName = entry.getValue().getName();
                line.departmentName = entry.getValue().getDepartment().getName();
                line.jobTitle = entry.getValue().getJob().getTitle();
                line.order = entry.getKey();
                return line;
            }).toList();
        }
    }

    public static ApprovalDocumentTemplateResponse of(DocumentTemplate template, Map<Integer, Employee> lineMap) {
        ApprovalDocumentTemplateResponse response = new ApprovalDocumentTemplateResponse();
        response.id = template.getId();
        response.title = template.getTitle();
        response.document = template.getDocument();
        if (lineMap == null) {
            response.autoApprovalLine = new ArrayList<>();
        } else {
            response.autoApprovalLine = AutoApprovalLine.of(lineMap);
        }
        return response;
    }
}
