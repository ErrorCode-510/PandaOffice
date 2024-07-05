package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response;

import com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response.CalculatedOverTimeAndLatenessRecordResponse;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedAttendanceAndOverTimeRecordResponse {

    private AttendanceRecordResponse attendanceRecordResponse;
    private CalculatedOverTimeAndLatenessRecordResponse calculatedOverTimeAndLatenessRecordResponse;

}
