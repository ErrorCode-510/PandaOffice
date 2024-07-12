package com.errorCode.pandaOffice.e_approval.dto;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class EmployeeResponse {
    private int employeeId;
    private String name;
    private JobResponse job;
    private DepartmentResponse department;

    public static EmployeeResponse of(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .job(JobResponse.builder()
                        .id(employee.getJob().getId())
                        .title(employee.getJob().getTitle())
                        .build())
                .department(DepartmentResponse.builder()
                        .id(employee.getDepartment().getId())
                        .name(employee.getDepartment().getName())
                        .build())
                .build();
    }
}
