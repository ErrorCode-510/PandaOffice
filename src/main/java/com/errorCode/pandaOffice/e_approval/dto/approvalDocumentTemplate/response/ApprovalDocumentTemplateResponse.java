package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
@Getter
@Builder
public class ApprovalDocumentTemplateResponse {

    /* 템플릿 ID */
    private int id;
    /* 템플릿 이름 */
    private String title;
    /* 템플릿 설명 */
    private String description;
    /* 기안자 정보 */
    private EmployeeResponse draftEmployee;
    /* 문서 내용 */
    private String document;
    /* 사용 상태 */
    private boolean status;
    /* 생성된 결재선의 리스트 */
    private List<AutoApprovalLine> autoApprovalLine;


    public static ApprovalDocumentTemplateResponse of(Employee draftEmployee, DocumentTemplate template, Map<Integer, Employee> approvalLineMap) {
        return ApprovalDocumentTemplateResponse.builder()
                .id(template.getId())
                .title(template.getTitle())
                .description(template.getDescription())
                .document(template.getDocument())
                .status(template.isStatus())
                .draftEmployee(EmployeeResponse.builder()
                        .employeeId(draftEmployee.getEmployeeId())
                        .name(draftEmployee.getName())
                        .job(EmployeeResponse.JobResponse.builder()
                                .id(draftEmployee.getJob().getId())
                                .title(draftEmployee.getJob().getTitle())
                                .build())
                        .department(EmployeeResponse.DepartmentResponse.builder()
                                .id(draftEmployee.getDepartment().getId())
                                .name(draftEmployee.getDepartment().getName())
                                .build())
                        .build())
                .autoApprovalLine(approvalLineMap.entrySet().stream().map(lineMap -> AutoApprovalLine.of(lineMap)).toList())
                .build();
    }

    @ToString
    @Getter
    @Builder
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


        public static AutoApprovalLine of(Map.Entry<Integer, Employee> lineMap) {
            return AutoApprovalLine.builder()
                    .employeeId(lineMap.getValue().getEmployeeId())
                    .employeeName(lineMap.getValue().getName())
                    .departmentName(lineMap.getValue().getDepartment().getName())
                    .jobTitle(lineMap.getValue().getJob().getTitle())
                    .order(lineMap.getKey())
                    .build();
        }
    }

    @ToString
    @Getter
    @Builder
    public static class EmployeeResponse {
        private int employeeId;
        private String name;
        private JobResponse job;
        private DepartmentResponse department;

        @ToString
        @Getter
        @Builder
        private static class JobResponse {
            private int id;
            private String title;
        }

        @ToString
        @Getter
        @Builder
        private static class DepartmentResponse {
            private int id;
            private String name;
        }
    }
}
