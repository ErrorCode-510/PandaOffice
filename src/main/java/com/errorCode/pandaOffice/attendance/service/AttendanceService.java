package com.errorCode.pandaOffice.attendance.service;

import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.OvertimeRecord;
import com.errorCode.pandaOffice.attendance.domain.repository.AttendanceRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.OvertimeRecordRepository;
import com.errorCode.pandaOffice.attendance.dto.AttendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.OvertimeRecord.response.OverTimeRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;

    private final OvertimeRecordRepository overtimeRecordRepository;

    /* 1. 사원의 아이디를 기준으로 모든 근태 기록을 보여주는 기능 */
    public List<AttendanceRecordResponse> getAttendanceRecord(int employeeId) {
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByEmployeeId(employeeId);
        return attendanceRecords.stream()
                .map(attendanceRecord -> new AttendanceRecordResponse(
                        attendanceRecord.getId(),
                        attendanceRecord.getDate(),
                        attendanceRecord.getCheckInTime(),
                        attendanceRecord.getCheckOutTime(),
                        attendanceRecord.getTotalLateTime()))
                .collect(Collectors.toList());
    }

    /* 2. 사원의 아이디를 기준으로 모든 연장 근무 기록을 보여주는 기능 */
    public List<OverTimeRecordResponse> getOvertimeRecord(int employeeId) {
        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployeeId(employeeId);

        return overtimeRecords.stream()
                .map(overtimeRecord -> new OverTimeRecordResponse(
                        overtimeRecord.getId(),
                        overtimeRecord.getStartDate(),
                        overtimeRecord.getEndDate(),
                        overtimeRecord.getType()
                ))
                .collect(Collectors.toList());
    }

    /* 3. 사원의 아이디를 기준으로 */

}
