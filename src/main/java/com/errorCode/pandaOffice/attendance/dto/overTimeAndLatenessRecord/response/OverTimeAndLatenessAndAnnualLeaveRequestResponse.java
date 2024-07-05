package com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response;

import com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response.AnnualLeaveUsedRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceSummaryResponse;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OverTimeAndLatenessAndAnnualLeaveRequestResponse {

    private AttendanceSummaryResponse attendanceSummary;
    private OverTimeAndLatenessRecordRequestResponse overTimeRecordsForToday;
    private OverTimeAndLatenessRecordRequestResponse overTimeRecords;
    private AnnualLeaveUsedRecordResponse usedLeaveRecordsForToday;
    private AnnualLeaveUsedRecordResponse annualLeaveUsedRecords;

}
