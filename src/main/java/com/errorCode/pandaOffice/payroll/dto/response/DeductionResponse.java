package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.DeductionRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductionResponse {

    private final int deductionId;
    private final DeductionCategoryResponse deductionCategory;
    private final int amount;

    public static DeductionResponse from(DeductionRecord deductionRecord) {
        DeductionCategoryResponse deductionCategoryResponse = DeductionCategoryResponse.from(deductionRecord.getDeductionCategory());

        return new DeductionResponse(
                deductionRecord.getDeductionId(),
                deductionCategoryResponse,
                deductionRecord.getAmount()
        );
    }
}
