package com.errorCode.pandaOffice.attendance.presectation;

import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceAndOvertimeRecordResponse;
import com.errorCode.pandaOffice.attendance.service.AttendanceService;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.ApprovalDocumentDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/management/status")
    public ResponseEntity<AttendanceAndOvertimeRecordResponse> getCalculatedAttendanceAndOvertimeRecord(
            @RequestParam("searchDate") LocalDate searchDate) {

        AttendanceAndOvertimeRecordResponse response = attendanceService.getCalculatedAttendanceAndOvertimeRecord(searchDate);
        return ResponseEntity.ok(response);
    }





}
