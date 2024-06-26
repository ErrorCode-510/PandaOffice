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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /* 1. 요청 받기 */
    @GetMapping("/attendance-record")
    public ResponseEntity<AttendanceRecordResponse> getAttendanceRecord(@RequestParam int employeeId){

        /* 2. 서비스 객체의 getAttendanceRecord 메소드에 사번 전달하여 근태 기록 가져오기 */
        List<AttendanceRecordResponse> attendanceRecordResponse = attendanceService.getAttendanceRecord(employeeId);

        return null;
    }

    @GetMapping("/overtime")
    public ResponseEntity<OverTimeRecordResponse> getOvertimeRecord(@RequestParam int employeeId) {

        /* 3. 서비스 객체의 getOvertimeRecord 메소드에 사번을 전달하여 연장근무 기록 가져오기 */
        List<OverTimeRecordResponse> overTimeRecordResponses = attendanceService.getOvertimeRecord(employeeId);

        return null;

    }

    @GetMapping("/annual-leave")
    public ResponseEntity<AnnualLeaveRecordResponse> getAnnualLeaveRecord(@RequestParam LocalDate searchStartDate,
                                                                          @RequestParam LocalDate searchEndDate) {

        /* 4. 서비스 객체의 getOvertimeRecord 메소드에 사번을 전달하여 연차 기록 가져오기 */
        List<AnnualLeaveRecordResponse> annualLeaveRecordResponses = attendanceService.getAnnualLeaveRecord(searchStartDate, searchEndDate);

        return null;

    }

    /* 5. 해당 월, 주의 누적 근무 시간 불러오기 */
    @GetMapping("/1")
    public ResponseEntity<AttendanceRecordResponse> calculateAttendanceTime(@RequestParam int employeeId ) {

        List<AttendanceRecordResponse> attendanceTime = attendanceService.calculateAttendanceTime(employeeId);

        return null;

    }

    /* 6. 해당 월, 주의 누적 초과 근무 시간 불러오기 */
    @GetMapping("/2")
    public ResponseEntity<OverTimeRecordResponse> calculateOverTime(@RequestParam int employeeId) {

        List<OverTimeRecordResponse> overTime = attendanceService.calculateOverTime(employeeId);

        return null;

    }

    /* 7. 해당 년도의 연차 내역 불러오기  */
    @GetMapping("/3")
    public ResponseEntity<AnnualLeaveRecordResponse> countAnnualLeave(@RequestParam LocalDate startDate,
                                                                      @RequestParam LocalDate endDate) {

        List<AnnualLeaveRecordResponse> everyAnnualLeave = attendanceService.getAnnualLeaveRecord(startDate, endDate);

        return null;

    }


}
