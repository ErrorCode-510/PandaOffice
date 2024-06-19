package com.errorCode.pandaOffice.employee.dto.response;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileResponse {

    private final int employeeId;
    private final String employeeName;



    public static ProfileResponse from(Employee employee) {

        return new ProfileResponse(
                employee.getEmployeeId(),
                employee.getName()

        );
    }
}
