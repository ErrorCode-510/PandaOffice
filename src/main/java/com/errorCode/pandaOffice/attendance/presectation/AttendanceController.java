package com.errorCode.pandaOffice.attendance.presectation;

import com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response.*;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.CalculatedAttendanceAndOverTimeRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response.OverTimeAndLatenessAndAnnualLeaveRequestResponse;
import com.errorCode.pandaOffice.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 1. 내 근태 현황 페이지 매핑
    @GetMapping("/management/status")
    public ResponseEntity<CalculatedAttendanceAndOverTimeRecordResponse> getCalculatedAttendanceAndOvertimeRecord(
            @RequestParam(value = "searchDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchDate) {

        if (searchDate == null) {
            searchDate = LocalDate.now();
        }

        CalculatedAttendanceAndOverTimeRecordResponse response = attendanceService.getCalculatedAttendanceAndOvertimeRecord(searchDate);
        return ResponseEntity.ok(response);
    }

    // 2. 내 연차 내역 페이지 매핑
    // 하나의 엔드포인트로 연차 기록 반환
    @GetMapping("/management/annual_leave_record")
    public ResponseEntity<CombinedAnnualLeaveResponse> getAnnualLeaveRecords(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        CalculateAnnualLeaveRecordResponse calculateResponse;
        AnnualLeaveUsedRecordResponse usedResponse;
        AnnualLeaveGrantRecordResponse grantResponse;

        if (startDate == null || endDate == null) {
            calculateResponse = attendanceService.getAnnualLeaveRecordForCurrentYear();
            usedResponse = attendanceService.getUsedLeaveRecordsForCurrentYear();
            grantResponse = attendanceService.getGrantLeaveRecordsForCurrentYear();
        } else {
            calculateResponse = attendanceService.getAnnualLeaveRecord(startDate, endDate);
            usedResponse = attendanceService.getUsedLeaveRecords(startDate, endDate);
            grantResponse = attendanceService.getGrantLeaveRecords(startDate, endDate);
        }

        return ResponseEntity.ok(new CombinedAnnualLeaveResponse(calculateResponse, usedResponse, grantResponse));
    }
    // 3. 연차 캘린더 조회
    // 연차 캘린더 데이터를 반환
    @GetMapping("/management/annual_leave_calendar")
    public ResponseEntity<AnnualLeaveRecordCalendarsResponse> getAnnualCalendar(
            @RequestParam(value = "searchDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchDate) {

        AnnualLeaveRecordCalendarsResponse response;
        if (searchDate == null) {
            response = attendanceService.getAnnualCalendarForCurrentMonth();
        } else {
            response = attendanceService.getAnnualCalendar(searchDate);
        }
        return ResponseEntity.ok(response);
    }

    // 4. 내 근태 신청 현황
    @GetMapping("/request_status")
    public ResponseEntity<OverTimeAndLatenessAndAnnualLeaveRequestResponse> getCombinedAttendanceResponse(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null) {
            startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        OverTimeAndLatenessAndAnnualLeaveRequestResponse response = attendanceService.getCombinedAttendanceResponse(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    /*5. 연차 조정 */
    // 모든 사원의 현재 연도의 모든 연차 기록 반환
    @GetMapping("/all_leave_adjustment")
    public ResponseEntity<AllLeaveRecordsResponse> getAllLeaveRecordsForCurrentYear() {
        AllLeaveRecordsResponse response = attendanceService.getAllLeaveRecordsForCurrentYear();
        return ResponseEntity.ok(response);
    }

    // 특정 연도에 입사한 사원들의 현재 연도의 모든 연차 기록 반환
    @GetMapping("/all_leave_adjustment/search")
    public ResponseEntity<AllLeaveRecordsResponse> getAllLeaveRecordsForEmployeesHiredInYear(
            @RequestParam(value = "hireYear") int hireYear) {
        AllLeaveRecordsResponse response = attendanceService.getAllLeaveRecordsForEmployeesHiredInYear(hireYear);
        return ResponseEntity.ok(response);
    }

}
