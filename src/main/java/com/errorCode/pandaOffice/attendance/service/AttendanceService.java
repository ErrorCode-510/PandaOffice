package com.errorCode.pandaOffice.attendance.service;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.OverTimeAndLatenessRecord;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveGrantRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveUsedRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AttendanceRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.OverTimeAndLatenessRecordRepository;
import com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response.*;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.request.AttendanceRecordRequest;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.CalculatedAttendanceAndOverTimeRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceSummaryResponse;
import com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response.CalculatedOverTimeAndLatenessRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response.OverTimeAndLatenessAndAnnualLeaveRequestResponse;
import com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response.OverTimeAndLatenessRecordRequestResponse;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final OverTimeAndLatenessRecordRepository overTimeAndLatenessRecordRepository;
    private final AnnualLeaveGrantRecordRepository annualLeaveGrantRecordRepository;
    private final AnnualLeaveUsedRecordRepository annualLeaveUsedRecordRepository;

    private final EmployeeRepository employeeRepository;

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1.내 근태 현황 페이지(Attendance Status) - 끝 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    /* 확인 사항
     * 1. 처음에 페이지 들어갔을 때 현재 날짜의 기록이 나오는가?
     * 2. 검색 받은 날짜를 기준으로 기록이 나오는가?
     * 3. Response가 제대로 쓰였는지 꼭 확인하기! */

    // 한 달 동안의 근태 기록과 초과 근무 기록을 계산하여 반환
    public CalculatedAttendanceAndOverTimeRecordResponse getCalculatedAttendanceAndOvertimeRecord(LocalDate searchDate) {
        int employeeId = TokenUtils.getEmployeeId();

        YearMonth yearMonth = YearMonth.from(searchDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<OverTimeAndLatenessRecord> overTimeAndLatenessRecords = overTimeAndLatenessRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);

        AttendanceRecordResponse attendanceResponse = AttendanceRecordResponse.of(attendanceRecords);
        CalculatedOverTimeAndLatenessRecordResponse calculatedOverTimeAndLatenessRecordResponse = CalculatedOverTimeAndLatenessRecordResponse.of(overTimeAndLatenessRecords);

        return new CalculatedAttendanceAndOverTimeRecordResponse(attendanceResponse, calculatedOverTimeAndLatenessRecordResponse);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 2.내 연차 내역(Annual Leave Record) - 끝 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    /* 확인 사항
     * 1. 처음에 페이지 들어갔을 때 현재 날짜의 기록이 나오는가?
     * 2. 검색 받은 날짜를 기준으로 기록이 나오는가? */

    // 현재 연도의 연차 기록을 계산하여 반환
    public CalculateAnnualLeaveRecordResponse getAnnualLeaveRecordForCurrentYear() {
        int employeeId = TokenUtils.getEmployeeId();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);

        return CalculateAnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    // 특정 기간 동안의 연차 기록을 반환
    public CalculateAnnualLeaveRecordResponse getAnnualLeaveRecord(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();
        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);

        return CalculateAnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    // 현재 연도의 소진 연차 기록을 반환
    /* 관리자 - 연차 조정 쪽에서도 사용함! */
    public AnnualLeaveUsedRecordResponse getUsedLeaveRecordsForCurrentYear() {
        int employeeId = TokenUtils.getEmployeeId();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);
        return AnnualLeaveUsedRecordResponse.of(usedRecords);
    }

    // 현재 연도의 부여 연차 기록을 반환
    /* 관리자 - 연차 조정 쪽에서도 사용함! */
    public AnnualLeaveGrantRecordResponse getGrantLeaveRecordsForCurrentYear() {
        int employeeId = TokenUtils.getEmployeeId();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        return AnnualLeaveGrantRecordResponse.of(grantRecords);
    }

    // 특정 기간 동안의 소진 연차 기록을 반환
    public AnnualLeaveUsedRecordResponse getUsedLeaveRecords(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);
        return AnnualLeaveUsedRecordResponse.of(usedRecords);
    }

    // 특정 기간 동안의 부여 연차 기록을 반환
    public AnnualLeaveGrantRecordResponse getGrantLeaveRecords(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();
        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        return AnnualLeaveGrantRecordResponse.of(grantRecords);
    }
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 3.연차 캘린더(Attendance Calendar) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /* 확인 사항
     * 1. 처음에 페이지 들어갔을 때 현재 날짜의 기록이 나오는가?
     * 2. 검색 받은 날짜를 기준으로 기록이 나오는가? */

    // 이번달의 연차 기록을 나타내줌
    public AnnualLeaveRecordCalendarsResponse getAnnualCalendarForCurrentMonth() {
        return getAnnualCalendar(LocalDate.now());
    }

    // 검색 받은 달의 연차 기록을 나타내줌
    public AnnualLeaveRecordCalendarsResponse getAnnualCalendar(LocalDate searchDate) {

        YearMonth yearMonth = YearMonth.from(searchDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByUsedStartDateBetween(startDate, endDate);
        return AnnualLeaveRecordCalendarsResponse.of(usedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 4.내 근태 신청 현황(Attendance Request Status) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /* 확인 사항
     * 1. 처음에 페이지 들어갔을 때 현재 날짜의 기록이 나오는가?
     * 2. 검색 받은 날짜를 기준으로 기록이 나오는가? */

    // 1. 맨위에 지각, 연장, 휴일 근무, 연차 횟수 나타내기
    public AttendanceSummaryResponse getAttendanceSummary() {
        int employeeId = TokenUtils.getEmployeeId();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.now();

        int lateCount = (int) overTimeAndLatenessRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate).stream()
                .filter(record -> record.getType().equals("지각"))
                .count();

        int overtimeCount = (int) overTimeAndLatenessRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate).stream()
                .filter(record -> record.getType().equals("연장근무"))
                .count();

        int holidayWorkCount = (int) overTimeAndLatenessRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate).stream()
                .filter(record -> record.getType().equals("휴일근무"))
                .count();

        int annualLeaveCount = (int) annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate).stream()
                .count();

        return AttendanceSummaryResponse.of(lateCount, overtimeCount, holidayWorkCount, annualLeaveCount);
    }

    public OverTimeAndLatenessRecordRequestResponse getOverTimeRecords(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();
        List<OverTimeAndLatenessRecord> overTimeAndLatenessRecords = overTimeAndLatenessRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        return OverTimeAndLatenessRecordRequestResponse.of(overTimeAndLatenessRecords);
    }

    public OverTimeAndLatenessAndAnnualLeaveRequestResponse getCombinedAttendanceResponse(LocalDate startDate, LocalDate endDate) {
        // 1. 맨 위에 현재 년도의 근태 신청 기록들을 나타냄
        AttendanceSummaryResponse summary = getAttendanceSummary();

        // 2. 처음에는 현재 날짜의 근태 신청 기록들을 나타내고
        OverTimeAndLatenessRecordRequestResponse recordsForToday = getOverTimeRecords(LocalDate.now(), LocalDate.now());
        AnnualLeaveUsedRecordResponse usedLeaveRecordsForToday = getUsedLeaveRecords(LocalDate.now(), LocalDate.now());

        // 3. 검색 기간을 받으면 검색 기간에 해당하는 근태 신청 기록들을 나타냄
        OverTimeAndLatenessRecordRequestResponse overTimeRecords = getOverTimeRecords(startDate, endDate);
        AnnualLeaveUsedRecordResponse annualLeaveUsedRecords = getUsedLeaveRecords(startDate, endDate);

        return new OverTimeAndLatenessAndAnnualLeaveRequestResponse(summary, recordsForToday, overTimeRecords, usedLeaveRecordsForToday, annualLeaveUsedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 5.연차 조정(Annual Leave Adjustment) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /* 확인 사항
     * 1. 처음에 페이지 들어갔을 때 모든 사원의 현재 날짜를 기준으로 한 기록이 나오는가?
     * 2. 검색 년도에 해당하는 입사일을 가진 사원들의 현재 년도 연차 기록이 나오는가? */

    // 모든 사원의 현재 연도의 모든 연차 기록 반환
    public AllLeaveRecordsResponse getAllLeaveRecordsForCurrentYear() {
        LocalDate currentYearStart = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate currentYearEnd = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByDateBetween(currentYearStart, currentYearEnd);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByUsedStartDateBetween(currentYearStart, currentYearEnd);

        return AllLeaveRecordsResponse.of(grantRecords, usedRecords);
    }

    public AllLeaveRecordsResponse getAllLeaveRecordsForEmployeesHiredInYear(int hireYear) {
        LocalDate hireStartDate = LocalDate.of(hireYear, 1, 1);
        LocalDate hireEndDate = LocalDate.of(hireYear, 12, 31);

        // 특정 연도에 입사한 사원들의 연차 기록 조회
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_HireDateBetween(hireStartDate, hireEndDate);

        // 특정 연도에 입사한 사원들의 현재 연도의 연차 부여 기록 조회
        List<String> employeeNames = usedRecords.stream()
                .map(record -> record.getEmployee().getName())
                .distinct()
                .collect(Collectors.toList());

        LocalDate currentYearStart = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate currentYearEnd = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_NameInAndDateBetween(employeeNames, currentYearStart, currentYearEnd);

        return AllLeaveRecordsResponse.of(grantRecords, usedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 6.출퇴근 등록(Attendance Regist ) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    public int saveCheckInTime(LocalDate date, LocalTime checkInTime) {
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다."));

        boolean exists = attendanceRecordRepository.existsByEmployeeAndDate(employee, date);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 해당 날짜에 출근 기록이 존재합니다.");
        }

        AttendanceRecord newRecord = AttendanceRecord.of(date, checkInTime, null, employee);
        final AttendanceRecord attendanceRecord = attendanceRecordRepository.save(newRecord);
        return attendanceRecord.getId();
    }

    public int saveCheckOutTime(LocalDate date, LocalTime checkOutTime) {
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다."));

        AttendanceRecord existingRecord = attendanceRecordRepository.findByEmployeeAndDate(employee, date)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "출근 기록을 찾을 수 없습니다."));

        if (existingRecord.getCheckInTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "출근 시간이 없습니다. 출근하지 않고 퇴근할 수 없습니다.");
        }

        if (existingRecord.getCheckOutTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 퇴근하셨습니다.");
        }

        // DTO를 통해 엔티티 수정
        AttendanceRecord updatedRecord = AttendanceRecord.updateCheckOutTime(existingRecord, checkOutTime);
        final AttendanceRecord savedRecord = attendanceRecordRepository.save(updatedRecord);
        return savedRecord.getId();
    }

}
