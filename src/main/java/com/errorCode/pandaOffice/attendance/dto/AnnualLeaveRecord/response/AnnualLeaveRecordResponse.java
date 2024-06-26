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

    /* 발생 연차 */
    private double generatedLeave;

    /* 조정 연차 */
    private double adjustedLeave;

    /* 총 연차 */
    private double totalLeave;

    /* 사용 연차 */
    private double usedLeave;

    /* 잔여 연차 */
    private double remainingLeave;

    public AnnualLeaveRecordResponse(int id, LocalDate date, double nowAmount, double amount) {
        this.id = id;
        this.date = date;
        this.nowAmount = nowAmount;
        this.amount = amount;
    }

    public AnnualLeaveRecordResponse(double generatedLeave, double adjustedLeave, double totalLeave, double usedLeave, double remainingLeave) {
        this.generatedLeave = generatedLeave;
        this.adjustedLeave = adjustedLeave;
        this.totalLeave = totalLeave;
        this.usedLeave = usedLeave;
        this.remainingLeave = remainingLeave;
    }
}
