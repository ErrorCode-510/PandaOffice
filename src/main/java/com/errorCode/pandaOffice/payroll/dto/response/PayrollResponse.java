package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.PayrollRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@RequiredArgsConstructor
public class PayrollResponse {

    /* 사번 */
    private final int employeeId;

    /* 사원명 */
    private final String employeeName;

    /* 사원 직급 */
    private final String jobTitle;

    /* 사원 부서 */
    private final String departmentName;

    /* 은행명 */
    private final String bankName;

    /* 계좌번호 */
    private final String accountNumber;

    /* 급여일 */
    private final LocalDate payrollDate;

    /* 경로 */
    private final String payStubPath;

    /* 급여 지급 */
    private final List<EarningResponse> earningRecordList;

    /* 급여 공제 */
    private final List<DeductionResponse> deductionRecordList;

    public static PayrollResponse from(PayrollRecord payroll) {
        List<EarningResponse> earningResponses = payroll.getEarningRecordList().stream()
                .map(EarningResponse::from)
                .collect(Collectors.toList());
        List<DeductionResponse> deductionResponses = payroll.getDeductionRecordList().stream()
                .map(DeductionResponse::from)
                .collect(Collectors.toList());

        return new PayrollResponse(
                payroll.getEmployee().getEmployeeId(),
                payroll.getEmployee().getName(),
                payroll.getEmployee().getJob().getTitle(),
                payroll.getEmployee().getDepartment().getName(),
                payroll.getEmployee().getAccount().getBank(),
                payroll.getEmployee().getAccount().getAccountNumber(),
                payroll.getPayrollDate(),
                payroll.getPayStubPath(),
                earningResponses,
                deductionResponses
        );
    }
}
