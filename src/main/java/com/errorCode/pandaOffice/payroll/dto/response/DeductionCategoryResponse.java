package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.DeductionCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeductionCategoryResponse {

    /* 공제 항목 카테고리 코드 */
    private final int deductionCategoryId;

    /* 항목명 */
    private final String name;

    /* 공제율 */
    private final double deductionRate;

    public static DeductionCategoryResponse from(DeductionCategory deductionCategory) {
        return new DeductionCategoryResponse(
                deductionCategory.getDeductionCategoryId(),
                deductionCategory.getName(),
                deductionCategory.getDeductionRate()
        );
    }
}
