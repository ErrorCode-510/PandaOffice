package com.errorCode.pandaOffice.attendance.dto.AnnualLeaveCategory.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveCategoryResponse {

    /* 연차 분류 코드 */
    private int categoryId;

    /* 연차 분류 이름 */
    private String categoryName;

    /* 연차 기록 코드 */
    private int recordId;

    /* 연차 기록 날짜 */
    private LocalDate recordDate;

    /* 잔여 연차 */
    private double nowAmount;

    /* 연차 변동 수량 */
    private double amount;

    /* 신청 서류 코드 */
    private int approvalDocumentId;



}
