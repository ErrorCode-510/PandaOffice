package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response;

import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.dto.DepartmentResponse;
import com.errorCode.pandaOffice.e_approval.dto.EmployeeResponse;
import com.errorCode.pandaOffice.e_approval.dto.JobResponse;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
public class ApprovalDocumentTemplateResponse {
    /* 템플릿 ID */
    private int id;
    /* 템플릿 별칭 */
    private String name;
    /* 기안자 */
    private EmployeeResponse draftEmployee;
    /* 템플릿 타이틀 */
    private String title;
    /* 템플릿 설명 */
    private String description;
    /* 최종 수정자 */
    private EmployeeResponse lastEditor;
    /* 최종 수정일 */
    private LocalDate lastEditDate;
    /* 수정 내용 */
    private String editComment;
    /* 문서 내용 */
    private String document;
    /* 사용 상태 */
    private boolean status;
    /* 자동 결재선 */
    private List<AutoApprovalLineResponse> autoApprovalLine;
    /* 결재선 설정을 위한 사원 리스트 */
    private List<EmployeeResponse> employeeList;
    /* 결재선 설정을 위한 직급 리스트 */
    private List<JobResponse> jobList;
    /* 결재선 설정을 위한 부서 리스트 */
    private List<DepartmentResponse> departmentList;

    public static ApprovalDocumentTemplateResponse of(DocumentTemplate entity,
                                                      Employee draftEmployee,
                                                      List<Employee> employeeList,
                                                      List<Department> departmentList,
                                                      List<Job> jobList) {
        return ApprovalDocumentTemplateResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .draftEmployee(EmployeeResponse.of(draftEmployee))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .lastEditDate(entity.getLastEditDate())
                .lastEditor(EmployeeResponse.of(entity.getLastEditor()))
                .editComment(entity.getLastEditComment())
                .document(entity.getDocument())
                .status(entity.isStatus())
                .autoApprovalLine(entity
                        .getAutoApprovalLines()
                        .stream()
                        .map(line->AutoApprovalLineResponse.builder()
                                .order(line.getOrder())
                                .employeeId(line.getEmployeeId())
                                .jobId(line.getJobId())
                                .departmentId(line.getDepartmentId())
                                .build())
                        .collect(Collectors.toList())
                )
                .employeeList(employeeList
                        .stream()
                        .map(emp->EmployeeResponse.of(emp))
                        .toList())
                .departmentList(departmentList
                        .stream()
                        .map(dept->DepartmentResponse.builder()
                                .id(dept.getId())
                                .name(dept.getName())
                                .build())
                        .toList())
                .jobList(jobList
                        .stream()
                        .map(job -> JobResponse.builder()
                                .id(job.getId())
                                .title(job.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
