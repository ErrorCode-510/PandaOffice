package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.PayrollRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PayrollResponse {

    private final int id;
    private final LocalDate payrollDate;
    private final String payStubPath;
    private final List<EarningResponse> earningRecordList;
    private final List<DeductionResponse> deductionRecordList;

//    public static PayrollResponse from(final PayrollRecord payroll) {
//        // EarningRecord 리스트를 EarningResponse 리스트로 변환
//        List<EarningResponse> earningResponses = payroll.getEarningRecordList().stream()
//                .map(EarningResponse::from)
//                .collect(Collectors.toList());
//
//        // DeductionRecord 리스트를 DeductionResponse 리스트로 변환
//        List<DeductionResponse> deductionResponses = payroll.getDeductionRecordList().stream()
//                .map(DeductionResponse::from)
//                .collect(Collectors.toList());
//
//        return new PayrollResponse(
//                payroll.getId(),
//                payroll.getPayrollDate(),
//                payroll.getPayStubPath(),
//                earningResponses,
//                deductionResponses
//        );
//    }
}
