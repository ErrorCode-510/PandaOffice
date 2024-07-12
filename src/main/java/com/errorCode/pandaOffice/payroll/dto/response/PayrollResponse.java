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

    private final int employeeId;
    private final String name;
    private final LocalDate payrollDate;
    private final String payStubPath;
    private final List<EarningResponse> earningRecordList;
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
                payroll.getPayrollDate(),
                payroll.getPayStubPath(),
                earningResponses,
                deductionResponses
        );
    }
}
