package com.errorCode.pandaOffice.attendance.service;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.OvertimeRecord;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveGrantRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveUsedRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AttendanceRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.OvertimeRecordRepository;
import com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response.AnnualLeaveRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceAndOvertimeRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.combined.response.CombinedRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.overtimeRecord.response.OverTimeRecordResponse;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final OvertimeRecordRepository overtimeRecordRepository;
    private final AnnualLeaveGrantRecordRepository annualLeaveGrantRecordRepository;
    private final AnnualLeaveUsedRecordRepository annualLeaveUsedRecordRepository;


    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1.내 근태 현황 페이지(Attendance Status)의 기능들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    // 패치노트
    // 1. attendanceRecord 엔티티 수정함(누적 시간, 잔여 시간 필드 추가)
    // 2. OvertimeRecord 엔티티 수정함(AttendanceRecord와 거의 비슷하게 수정 - 누적 초과 시간 필드 추가 , date 필드 추가 , 날짜를 시간으로 변경 )
    // 3. 주별 누적 시간 계산 메소드 추가

    /* 한 달 동안의 근태 기록과 초과 근무 기록을 계산하여 반환 */
    public AttendanceAndOvertimeRecordResponse getCalculatedAttendanceAndOvertimeRecord(LocalDate searchDate) {

        // 토큰에서 사원 ID 가져오는 메소드
        int employeeId = TokenUtils.getEmployeeId();

        YearMonth yearMonth = YearMonth.from(searchDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 사원 id에 맞는 출석 기록 entity 가져오기
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);

        // 사원 id에 맞는 초과 근무 기록 entity 가져오기
        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);

        // AttendanceRecord 리스트를 AttendanceRecordResponse로 변환
        AttendanceRecordResponse attendanceResponse = AttendanceRecordResponse.of(attendanceRecords);

        // OvertimeRecord 리스트를 OverTimeRecordResponse로 변환
        OverTimeRecordResponse overtimeResponse = OverTimeRecordResponse.of(overtimeRecords);

        // 두 응답을 합쳐서 반환
        return new AttendanceAndOvertimeRecordResponse(attendanceResponse, overtimeResponse);
    }

    // 서비스 부분에서는 기능을 다 구현함 컨트롤러 부분에서 필요한 부분만 떼가서 쓰면 됨
    /* 3. 표에 보이는 부분들을 나타낼 수 있도록 제작 */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 2.내 연차 내역(Annual Leave Record)의 기능들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    // 패치노트
    // 1.연차의 엔티티 수정 (연차 기록 카테고리 엔티티에서 분류 필드 추가함)

    // 특정 기간 동안의 연차 기록을 계산하여 반환
    public AnnualLeaveRecordResponse getAnnualLeaveRecord(LocalDate startDate, LocalDate endDate) {

        // 토큰에서 사원 ID 가져오는 메소드
        int employeeId = TokenUtils.getEmployeeId();

        // 사원 id에 맞는 연차 부여 및 사용 기록 entity 가져오기
        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);

        // AnnualLeaveRecordResponse 객체로 변환
        return AnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 3.연차 캘린더(Attendance Calendar)  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    // 특정 월의 연차 캘린더 데이터를 반환
    public AnnualLeaveRecordResponse getAnnualCalendar(LocalDate searchDate) {

        // 검색할 달의 첫 날과 마지막 날을 계산
        YearMonth yearMonth = YearMonth.from(searchDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 해당 기간의 연차 부여 및 사용 기록 가져오기
        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByDateBetween(startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByUsedStartDateBetween(startDate, endDate);

        // AnnualLeaveRecordResponse 객체로 변환
        return AnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 4.내 근태 신청 현황(Attendance Input Status) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 사원의 근태 신청 현황을 표 형식으로 보여줄건데
    *  1. 지각 : 근태 기록에서 지각한 날짜가 몇개인지 코드를 짜서 그 근태 번호에 모든 기록을 나타내도록 작성
        2. 연장 근무 : 연장 근무 테이블에서 연장 근무 타입에 대한 수를 종합하면 됨
        3. 휴일 근무 : 위랑 같음
        4. 연차 : 연차 테이블에서 소진 연차만 종합해서 하면 됨
        5. 총합은 위에 숫자들 다 더하면 됨 */

    /* 2.조회 기간 : 프론트에서 날짜 전달 받고 계산해서 날짜에 해당하는 애들 결과로 내줄거임 */

    /* 3.근태 신청 현황 : 근태 기록과 연장 근무 기록 테이블에서 따와서 보여주면 됨 */

    /* 4.연차 신청 현황 : 연차 기록 테이블에서 따와서 보여주면 됨 */

    // 연차 기록을 불러오는 메소드
    public AnnualLeaveRecordResponse getAnnualLeaveRecords(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);

        return AnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    // 연장근무 및 지각 기록을 불러오는 메소드
    public OverTimeRecordResponse getOvertimeRecords(LocalDate startDate, LocalDate endDate) {
        int employeeId = TokenUtils.getEmployeeId();

        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);

        return OverTimeRecordResponse.of(overtimeRecords);
    }



    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 5.연차 조정(Annual Leave Adjustment) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */


    /* 5.조정 정보 : 이거는 프론트에서 먼저 만들어야 하는데
    * 프론트에서 부여,소진 연차를 누르면 카테고리 설정이 되고
    * 조정 일수는 amount 컬럼에 숫자를 넣어줄거임
    *  1. 조정항목 : 버튼식이고 누르면 색이 바뀜
       2. 조정 일수 : 플마 눌러서 가능
    *  3. 합계 : 현재 상태(부여 연차, 소진연차)에서 계산된 값이 표시됨
       4. 저장 : 저장 버튼 누르면 팝업창이 뜨면서 저장이 완료됨 */

    // 특정 년도에 입사한 사원들의 현년도 연차 내역을 반환
    public AnnualLeaveRecordResponse getAnnualLeaveRecordsByHiredYear(int searchYear) {
        int currentYear = LocalDate.now().getYear();

        // 특정 년도의 입사일 범위를 설정
        LocalDate startOfHiredYear = LocalDate.of(searchYear, 1, 1);
        LocalDate endOfHiredYear = LocalDate.of(searchYear, 12, 31);

        // 해당 년도에 입사한 사원들의 연차 기록 가져오기
        List<AnnualLeaveGrantRecord> hiredYearGrantRecords = annualLeaveGrantRecordRepository.findByEmployee_HireDateBetween(startOfHiredYear, endOfHiredYear);
        List<AnnualLeaveUsedRecord> hiredYearUsedRecords = annualLeaveUsedRecordRepository.findByEmployee_HireDateBetween(startOfHiredYear, endOfHiredYear);

        // 현재 년도의 범위를 설정
        LocalDate startOfCurrentYear = LocalDate.of(currentYear, 1, 1);
        LocalDate endOfCurrentYear = LocalDate.of(currentYear, 12, 31);

        // 현재 년도에 해당하는 연차 부여 및 사용 기록 필터링
        List<AnnualLeaveGrantRecord> currentYearGrantRecords = hiredYearGrantRecords.stream()
                .filter(record -> !record.getDate().isBefore(startOfCurrentYear) && !record.getDate().isAfter(endOfCurrentYear))
                .collect(Collectors.toList());

        List<AnnualLeaveUsedRecord> currentYearUsedRecords = hiredYearUsedRecords.stream()
                .filter(record -> !record.getUsedStartDate().isBefore(startOfCurrentYear) && !record.getUsedStartDate().isAfter(endOfCurrentYear))
                .collect(Collectors.toList());

        // AnnualLeaveRecordResponse 객체로 변환
        return AnnualLeaveRecordResponse.of(currentYearGrantRecords, currentYearUsedRecords);
    }

    // 현재 년도의 부여 및 사용된 연차 기록을 반환
    public AnnualLeaveRecordResponse getGrantAndUsedLeave() {
        int employeeId = TokenUtils.getEmployeeId();
        int currentYear = LocalDate.now().getYear();

        LocalDate startDate = LocalDate.of(currentYear, 1, 1);
        LocalDate endDate = LocalDate.of(currentYear, 12, 31);

        List<AnnualLeaveGrantRecord> grantRecords = annualLeaveGrantRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);
        List<AnnualLeaveUsedRecord> usedRecords = annualLeaveUsedRecordRepository.findByEmployee_EmployeeIdAndUsedStartDateBetween(employeeId, startDate, endDate);

        return AnnualLeaveRecordResponse.of(grantRecords, usedRecords);
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 7.모든 사원 근태 현황(Attendance Status of All Employees) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 조건별 상세 검색
     * 1. 입사일을 기준으로 검색을 할거다.
     * 2. 사원 + 연차 테이블을 조인해서 나타내야한다.
     * 3. 근데 이거 전부 다 프론트라서.. 어카냐 지금..  */

    /* 2. 목록 : 이름이랑 직급이랑 같이 나오게 해야하는거 말고는 딱히 신경써야 할 부분이 없어보임 */

}
