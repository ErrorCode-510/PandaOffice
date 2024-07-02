package com.errorCode.pandaOffice.attendance.dto.combined.response;

import com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response.AnnualLeaveRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.overtimeRecord.response.OverTimeRecordResponse;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CombinedRecordResponse {

    // 근태 기록 응답 객체
    private final AttendanceRecordResponse attendanceRecordResponse;

    // 초과 근무 기록 응답 객체
    private final OverTimeRecordResponse overTimeRecordResponse;

    // 연차 기록 응답 객체
    private final AnnualLeaveRecordResponse annualLeaveRecordResponse;

}
