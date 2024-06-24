package com.errorCode.pandaOffice.attendance.presectation;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveCategory;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import com.errorCode.pandaOffice.attendance.dto.AnnualLeaveCategory.response.AnnualLeaveCategoryResponse;
import com.errorCode.pandaOffice.attendance.dto.AnnualLeaveRecord.response.AnnualLeaveRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.AttendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.OvertimeRecord.response.OverTimeRecordResponse;
import com.errorCode.pandaOffice.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /* 1. 요청 받기 */
    @GetMapping("attendance-status")
    public ResponseEntity<AttendanceRecordResponse> getAttendanceRecord(@RequestParam int employeeId){

        /* 2. 서비스 객체의 getAttendanceRecord 메소드에 사번 전달하여 근태기록 가져오기 */
        List<AttendanceRecordResponse> attendanceRecordResponse = attendanceService.getAttendanceRecord(employeeId);

        return null;
    }

    @GetMapping("attendance")
    public ResponseEntity<OverTimeRecordResponse> getOvertimeRecord(@RequestParam int employeeId) {

        /* 3. 서비스 객체의 getOvertimeRecord 메소드에 사번을 전달하여 근태 기록 가져오기 */
        List<OverTimeRecordResponse> overTimeRecordResponses = attendanceService.getOvertimeRecord(employeeId);

        return null;

    }

    @GetMapping("attendance")
    public ResponseEntity<AnnualLeaveRecord> getAnnualLeaveRecord(@RequestParam int employeeId) {

        /* 3. 서비스 객체의 getOvertimeRecord 메소드에 사번을 전달하여 근태 기록 가져오기 */
        List<AnnualLeaveRecordResponse> annualLeaveRecordResponses = attendanceService.getAnnualLeaveRecord(employeeId);

        return null;

    }

    @GetMapping("attendance")
    public ResponseEntity<AnnualLeaveCategory> getAnnualLeaveCategory(@RequestParam int employeeId) {

        /* 3. 서비스 객체의 getOvertimeRecord 메소드에 사번을 전달하여 근태 기록 가져오기 */
        List<AnnualLeaveCategoryResponse> annualLeaveCategoryResponses = attendanceService.getAnnualLeaveCategory(employeeId);

        return null;

    }


}
