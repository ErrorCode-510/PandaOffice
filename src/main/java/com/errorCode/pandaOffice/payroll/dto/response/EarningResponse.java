package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.EarningRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EarningResponse {

    private final int earningId;

    private final EarningCategoryResponse earningCategory;

    private final int amount;

    public static EarningResponse from(EarningRecord earningRecord) {
        EarningCategoryResponse earningCategoryResponse = EarningCategoryResponse.from(earningRecord.getEarningCategory());
        return new EarningResponse(
                earningRecord.getEarningId(),
                earningCategoryResponse,
                earningRecord.getAmount()
        );
    }
}
