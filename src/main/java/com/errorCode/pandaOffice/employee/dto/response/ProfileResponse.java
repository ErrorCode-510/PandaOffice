package com.errorCode.pandaOffice.employee.dto.response;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileResponse {

    private final String memberId;
    private final String memberName;
    private final String memberEmail;


    public static ProfileResponse from(Employee employee) {

        return new ProfileResponse(
                employee.getMemberId(),
                employee.getMemberName(),
                employee.getMemberEmail()
        );
    }
}
