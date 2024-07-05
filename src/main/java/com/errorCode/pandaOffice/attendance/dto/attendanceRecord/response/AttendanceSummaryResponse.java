package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSummaryResponse {

    private int lateCount;
    private int overtimeCount;
    private int holidayWorkCount;
    private int annualLeaveCount;
    private int totalCount;

    public static AttendanceSummaryResponse of(int lateCount, int overtimeCount, int holidayWorkCount, int annualLeaveCount) {

        int totalCount = lateCount + overtimeCount + holidayWorkCount + annualLeaveCount;

        return new AttendanceSummaryResponse(lateCount, overtimeCount, holidayWorkCount, annualLeaveCount, totalCount);
    }
}
