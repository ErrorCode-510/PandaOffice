package com.errorCode.pandaOffice.attendance.dto.AnnualLeaveCategory.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveCategoryResponse {

    /* 연차 분류 코드 */
    private int id;

    /* 연차 분류 이름 */
    private String name;

}
