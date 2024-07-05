package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
/* 사원의 사번, 이름, 직급, 연봉을 가져옴 */
public class EmplPayrollResponse {

    // 사원 번호
    private final int employeeId;
    // 사원 이름
    private final String name;
    // 직급
    private final String jobTitle;
    // 직책수당
    private final int jobAllowance;
    // 연봉
    private final int annualSalary;

    // 엔티티로부터 DTO를 생성하는 팩토리 메소드
    public static EmplPayrollResponse from(Employee employee) {
        return new EmplPayrollResponse(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getJob().getTitle(),
                employee.getJob().getAllowance(),
                employee.getAnnualSalary()
        );
    }

}
