package com.errorCode.pandaOffice.attendance.dto.AnnualLeaveRecord.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveRecordResponse {

    /* 연차 기록 코드 */
    private int id;

    /* 연차 기록 변동일 */
    private LocalDate date;

    /* 잔여 연차 */
    private double nowAmount;

    /* 수량 (연차를 얼마나 썼는지, 받았는지에 대한 수량임) */
    private double amount;

}
