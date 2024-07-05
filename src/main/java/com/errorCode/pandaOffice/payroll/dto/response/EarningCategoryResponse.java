package com.errorCode.pandaOffice.payroll.dto.response;

import com.errorCode.pandaOffice.payroll.domain.entity.EarningCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EarningCategoryResponse {

    /* 지급 항목 카테고리 코드 */
    private final int earningCategoryId;

    /* 급여사항명 */
    private final String name;

    /* 과세여부 */
    private final String isTax;

    public static EarningCategoryResponse from(EarningCategory earningCategory) {
        return new EarningCategoryResponse(
          earningCategory.getEarningCategoryId(),
          earningCategory.getName(),
          earningCategory.getIsTax()
        );
    }
}
