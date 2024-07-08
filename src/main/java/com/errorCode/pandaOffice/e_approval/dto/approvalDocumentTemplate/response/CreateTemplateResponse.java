package com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response;

import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class CreateTemplateResponse {
    private EmployeeResponse draftEmployee;
    private List<JobResponse> jobList;
    private List<DepartmentResponse> departmentList;
    private List<EmployeeResponse> employeeList;

    public static CreateTemplateResponse of(Employee draftEmployee, List<Job> jobEntityList, List<Department> departmentEntityList, List<Employee> employeeEntityList) {
        return CreateTemplateResponse.builder()
                .draftEmployee(EmployeeResponse.of(draftEmployee))
                .jobList(jobEntityList.stream()
                        .map(JobResponse::of)
                        .toList())
                .departmentList(departmentEntityList.stream()
                        .map(DepartmentResponse::of)
                        .toList())
                .employeeList(employeeEntityList.stream()
                        .map(EmployeeResponse::of)
                        .toList())
                .build();
    }

    @Getter
    @ToString
    @Builder
    private static class JobResponse{
        private int id;
        private String title;

        public static JobResponse of(Job entity) {
            return JobResponse.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .build();
        }
    }

    @Getter
    @ToString
    @Builder
    private static class DepartmentResponse{
        private int id;
        private String name;

        public static DepartmentResponse of(Department entity) {
            return DepartmentResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    @ToString
    @Builder
    private static class EmployeeResponse{
        private int employeeId;
        private String name;
        private JobResponse job;
        private DepartmentResponse department;

        public static EmployeeResponse of(Employee employee) {
            return EmployeeResponse.builder()
                    .employeeId(employee.getEmployeeId())
                    .name(employee.getName())
                    .job(JobResponse.of(employee.getJob()))
                    .department(DepartmentResponse.of((employee.getDepartment())))
                    .build();
        }
    }
}
