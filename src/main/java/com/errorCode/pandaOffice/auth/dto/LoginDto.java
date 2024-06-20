package com.errorCode.pandaOffice.auth.dto;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.type.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

    private final int employeeId;

    private final String password;


    public static LoginDto from(Employee member) {
        return new LoginDto(
                member.getEmployeeId(),

                member.getPassword()

        );
    }
}
