package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response;

import com.errorCode.pandaOffice.attendance.dto.overtimeRecord.response.OverTimeRecordResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AttendanceAndOvertimeRecordResponse {

    private final AttendanceRecordResponse attendanceRecordResponse;
    private final OverTimeRecordResponse overTimeRecordResponse;

}
