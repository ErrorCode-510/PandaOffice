package com.errorCode.pandaOffice.attendance.service;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveCategory;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.OvertimeRecord;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveCategoryRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AnnualLeaveRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.AttendanceRecordRepository;
import com.errorCode.pandaOffice.attendance.domain.repository.OvertimeRecordRepository;
import com.errorCode.pandaOffice.attendance.dto.AnnualLeaveCategory.response.AnnualLeaveCategoryResponse;
import com.errorCode.pandaOffice.attendance.dto.AnnualLeaveRecord.response.AnnualLeaveRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.AttendanceRecord.response.AttendanceRecordResponse;
import com.errorCode.pandaOffice.attendance.dto.OvertimeRecord.response.OverTimeRecordResponse;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;

    private final OvertimeRecordRepository overtimeRecordRepository;

    private final AnnualLeaveRecordRepository annualLeaveRecordRepository;

    private final AnnualLeaveCategoryRepository annualLeaveCategoryRepository;

    private final EmployeeRepository employeeRepository;


    /* 1. 사원의 아이디를 기준으로 모든 근태 기록을 보여주는 기능 */
    public List<AttendanceRecordResponse> getAttendanceRecord(int employeeId) {
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByEmployee_EmployeeId(employeeId);

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
        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployee_EmployeeId(employeeId);

        return overtimeRecords.stream()
                .map(overtimeRecord -> new OverTimeRecordResponse(
                        overtimeRecord.getId(),
                        overtimeRecord.getDate(),
                        overtimeRecord.getStartTime(),
                        overtimeRecord.getEndTime(),
                        overtimeRecord.getType()
                ))
                .collect(Collectors.toList());
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1.내 근태 현황 페이지(Attendance Status)의 기능들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    /* 1. 상단의 날짜를 프론트로부터 받아서 날짜에 따라 그 주에 해당하는 근태 기록들을 표로 확인하기 */

    /* 2. 상단의 표(사각형 5개)에 표시된 부분들 제작 */

    // 패치노트
    // 1. attendanceRecord 엔티티 수정함(누적 시간, 잔여 시간 필드 추가)
    // 2. OvertiemRecord 엔티티 수정함(attendanceRecord와 거의 비슷하게 수정 - 누적 초과 시간 필드 추가 , date 필드 추가 , 날짜를 시간으로 변경 )
    // 3. 주별 누적 시간 계산 메소드 추가

    /* 공통적으로 쓰는 메소드 */
    /* 1.해당 날짜의 주를 구하는 날짜 */
    public String getWeek(LocalDate date) {

        // 한 주의 시작은 월요일이고, 첫 주에 4일이 포함되어있어야 첫 주 취급 (목/ 금/ 토/ 일)
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);

        int weekOfMonth = date.get(weekFields.weekOfMonth());

        // 첫 주에 해당하지 않는 주의 경우 전 달 마지막 주차로 계산
        if (weekOfMonth == 0) {

            // 전 달의 마지막 날 기준
            LocalDate lastDayOfLastMonth = date.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);

            return getWeek(lastDayOfLastMonth);
        }

        // 이번 달의 마지막 날 기준
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        // 마지막 주차의 경우 마지막 날이 월~수 사이이면 다음달 1주차로 계산
        if (weekOfMonth == lastDayOfMonth.get(weekFields.weekOfMonth()) && lastDayOfMonth.getDayOfWeek().compareTo(DayOfWeek.THURSDAY) < 0) {

            // 마지막 날 + 1일 => 다음달 1일
            LocalDate firstDayOfNextMonth = lastDayOfMonth.plusDays(1);

            return getWeek(firstDayOfNextMonth);
        }

        return date.getYear() + "-" + date.getMonthValue() + "-W" + weekOfMonth;
    }

    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 누적 근무 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    /* 2 표에서 Attendance만 해당하는 부분 구현
    * 1. 이번 주 누적
    * 2. 이번 주 잔여
    * 3. 이번 달 누적 */
    public List<AttendanceRecordResponse> calculateAttendanceTime(int employeeId) {

        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByEmployee_EmployeeId(employeeId);

        // 이번 주 누적 근무 시간
        Map<String, Duration> weeklyCumulativeTimes = calculateWeeklyCumulativeTimes(attendanceRecords);

        // 이번 주 잔여 근무 시간
        Duration fortyHours = Duration.ofHours(40);

        // 이번 달 누적 근무 시간
        Map<String, Duration> monthlyCumulativeTimes = calculateMonthlyCumulativeTimes(attendanceRecords);

        // AttendanceRecordResponse 리스트 생성 및 반환
        return attendanceRecords.stream()
                .map(attendanceRecord -> {

                    // 전달받은 날짜에서 주를 계산
                    String week = getWeek(attendanceRecord.getDate());

                    // 전달받은 날짜에서 달을 계산
                    String month = attendanceRecord.getDate().getYear() + "-" + attendanceRecord.getDate().getMonthValue();

                    // weeklyCumulativeTimes 맵에서 weekKey에 해당하는 값을 가져오거나, 해당 키가 맵에 없을 경우 기본값인 Duration.ZERO(0시간)를 반환
                    Duration weeklyCumulativeTime = weeklyCumulativeTimes.getOrDefault(week, Duration.ZERO);

                    // 40시간에서 누적시간을 뺀 값
                    Duration remainingTime = fortyHours.minus(weeklyCumulativeTime);

                    // monthlyCumulativeTimes 맵에서 month에 해당하는 값을 가져오거나, 해당 키가 맵에 없을 경우 기본값인 Duration.ZERO(0시간)를 반환
                    Duration monthlyCumulativeTime = monthlyCumulativeTimes.getOrDefault(month, Duration.ZERO);

                    return new AttendanceRecordResponse(
                            attendanceRecord.getId(),
                            attendanceRecord.getDate(),
                            attendanceRecord.getCheckInTime(),
                            attendanceRecord.getCheckOutTime(),
                            attendanceRecord.getTotalLateTime(),
                            weeklyCumulativeTime,
                            remainingTime,
                            monthlyCumulativeTime
                    );
                })
                .collect(Collectors.toList());
    }


    /* 2-1. 이번 주 누적 근무 시간을 계산하는 메소드 */
    public Map<String, Duration> calculateWeeklyCumulativeTimes(List<AttendanceRecord> attendanceRecords) {

        // 주를 Key 값으로 받아서 누적 시간을 담아주는 Map 객체를 만든다.
        Map<String, Duration> weeklyCumulativeTimes = new HashMap<>();

        // 반복문을 통해서 전달받은 AttendanceRecord 목록의 date 값을 계속해서 추출해서 Key값으로 담아주고
        for (AttendanceRecord attendanceRecord : attendanceRecords) {

            String week = getWeek(attendanceRecord.getDate());

            // 출근 시간과 퇴근 시간 사이의 시간을 계산해서 노동 시간을 구한다.
            Duration workDuration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());

            // 주별 누적 시간 갱신해준다.
            weeklyCumulativeTimes.merge(week, workDuration, Duration::plus);
        }

        return weeklyCumulativeTimes;
    }

    /* 2-2 이번 달 누적 근무 시간을 계산하는 메소드 */
    public Map<String,Duration> calculateMonthlyCumulativeTimes(List<AttendanceRecord> attendanceRecords) {

        Map<String, Duration> monthlyCumulativeTimes = new HashMap<>();

        for (AttendanceRecord attendanceRecord : attendanceRecords) {

            // 날짜를 전달 받아서 년과 달을 Key 값으로 삼는다
            String month = attendanceRecord.getDate().getYear() + "-" + attendanceRecord.getDate().getMonthValue();

            // 출근 시간과 퇴근 시간 사이의 시간을 계산해서 노동 시간을 구한다.
            Duration workDuration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());

            // 달별 누적 시간 갱신해준다.
            monthlyCumulativeTimes.merge(month, workDuration, Duration::plus);
        }

        return monthlyCumulativeTimes;

    }

    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 초과 근무 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    /* 2-3. 표에서 overtime에 대한 부분만 나타내기
    * 1. 이번 주 초과 근무
    * 2. 이번 달 초과 근무 */
    public List<OverTimeRecordResponse> calculateOverTime(int employeeId) {
        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployee_EmployeeId(employeeId);

        Map<String, Duration> weeklyExtendedOvertimes = calculateWeeklyOverTimes(overtimeRecords, "연장");
        Map<String, Duration> weeklyHolidayOvertimes = calculateWeeklyOverTimes(overtimeRecords, "휴일");
        Map<String, Duration> monthlyExtendedOvertimes = calculateMonthlyOverTimes(overtimeRecords, "연장");
        Map<String, Duration> monthlyHolidayOvertimes = calculateMonthlyOverTimes(overtimeRecords, "휴일");

        return overtimeRecords.stream()
                .map(overtimeRecord -> {
                    String week = getWeek(overtimeRecord.getDate());
                    String month = overtimeRecord.getDate().getYear() + "-" + overtimeRecord.getDate().getMonthValue();

                    Duration weeklyOverTime = overtimeRecord.getType().equals("연장")
                            ? weeklyExtendedOvertimes.getOrDefault(week, Duration.ZERO)
                            : weeklyHolidayOvertimes.getOrDefault(week, Duration.ZERO);
                    Duration monthlyOverTime = overtimeRecord.getType().equals("연장")
                            ? monthlyExtendedOvertimes.getOrDefault(month, Duration.ZERO)
                            : monthlyHolidayOvertimes.getOrDefault(month, Duration.ZERO);

                    return new OverTimeRecordResponse(
                            overtimeRecord.getId(),
                            overtimeRecord.getDate(),
                            overtimeRecord.getStartTime(),
                            overtimeRecord.getEndTime(),
                            overtimeRecord.getType(),
                            weeklyOverTime,
                            monthlyOverTime
                    );
                })
                .collect(Collectors.toList());
    }

    /* 2-4. 주별 누적 초과 근무 */
    public Map<String, Duration> calculateWeeklyOverTimes(List<OvertimeRecord> overtimeRecords, String type) {
        Map<String, Duration> weeklyOvertimes = new HashMap<>();

        for (OvertimeRecord overtimeRecord : overtimeRecords) {
            if (overtimeRecord.getType().equals(type)) {
                String week = getWeek(overtimeRecord.getDate());
                Duration overWorkDuration = Duration.between(overtimeRecord.getStartTime(), overtimeRecord.getEndTime());
                weeklyOvertimes.merge(week, overWorkDuration, Duration::plus);
            }
        }

        return weeklyOvertimes;
    }

    /* 2-5. 월별 누적 초과 근무 */
    public Map<String, Duration> calculateMonthlyOverTimes(List<OvertimeRecord> overtimeRecords, String type) {
        Map<String, Duration> monthlyOverTimes = new HashMap<>();

        for (OvertimeRecord overtimeRecord : overtimeRecords) {
            if (overtimeRecord.getType().equals(type)) {
                String month = overtimeRecord.getDate().getYear() + "-" + overtimeRecord.getDate().getMonthValue();
                Duration overWorkDuration = Duration.between(overtimeRecord.getStartTime(), overtimeRecord.getEndTime());
                monthlyOverTimes.merge(month, overWorkDuration, Duration::plus);
            }
        }

        return monthlyOverTimes;
    }

    /* 3. 표에 보이는 부분들을 나타낼 수 있도록 제작 */

    // 서비스 부분에서는 기능을 다 구현함 컨트롤러 부분에서 필요한 부분만 떼가서 쓰면 됨

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 2.내 연차 내역(Annual Leave Record)의 기능들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    // 패치노트
    // 1.연차의 엔티티 수정 (연차 기록 카테고리 엔티티에서 분류 필드 추가함)

    /* 1. 사원의 연차를 표 형식으로 표현해줌  */

    /* 2. 연차 사용기간을 프론트에서 검색 받아서 사용 내역의 표가 변경된다.
     * 근데 사용 기간은 경우는 연차 기록 테이블에 있지 않기 때문에 검색하는 방법은
     * 등록일을 시작일로 받고 종료일을 등록일로부터 소진한 연차의 갯수만큼 더한 값을
     * 종료일로 해야한다.                                                    */

    /* 3. 생성 내역은 그냥 연차 기록에서 가져와서 값 나타내주면 됨
     * 유효기간 같은 경우는 등록일로부터 1년을 더하도록 로직을 짜면 되고
     * 윤년같은 경우는 366로 더하도록 로직을 짜야함                            */

    public List<AnnualLeaveRecordResponse> getAnnualLeaveRecord(LocalDate startDate, LocalDate endDate) {

        /* 토큰에서 사원 ID 가져오는 메소드 */
        int employeeId = TokenUtils.getEmployeeId();

        /* 사원 id에 맞는 연차 기록 entity 가져오기 */
        List<AnnualLeaveRecord> recordList = annualLeaveRecordRepository.findByEmployee_EmployeeIdAndDateBetween(employeeId, startDate, endDate);

        AnnualLeaveRecordResponse response = AnnualLeaveRecordResponse.of(recordList);

        return List.of(response);

    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 3.연차 캘린더(Attendance Calendar)  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 왼쪽 위에 화살표와 오늘 버튼 이거는 프론트쪽에서 작동하는 기능이지만
     * 화살표를 누르게 되면 비동기로 달력이 바뀌게 되는데
     * 달력이 바뀐 달에 맞춰서 달력을 바꿀거임
     * 즉 프론트에서 날짜가 바뀐다 -> 그 날짜를 rest에서 입력받는다 -> 입력받은 날짜에 맞춰서 값을 보내준다.
     * 연차의 값 중에서 소진한 연차의 값만 보내주면 된다. */

    public List<AnnualLeaveRecordResponse> getAnnualCalendar(LocalDate searchDate) {

        // 검색할 달의 첫 날과 마지막 날을 계산합니다.
        YearMonth yearMonth = YearMonth.from(searchDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 모든 사원의 연차 기록을 검색합니다.
        List<AnnualLeaveRecord> recordList = annualLeaveRecordRepository.findByDateBetween(startDate, endDate);

        AnnualLeaveRecordResponse response = AnnualLeaveRecordResponse.of(recordList);

        return List.of(response);
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

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 5.연차 조정(Annual Leave Adjustment) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 귀속년도 : 년도만 검색해서 해당년도의 연차 기록을 보여줄거임
     * 그니까 프론트에서 년도만 받고 그 년도에 해당하는 날짜를 계산하도록 작성하면 됨 */

    /* 2. 연차 정보 : 연차 기록을 연차 기록 카테고리와 함께 th문으로 나타내주면 됨 */

    /* 3. 부여 연차 : 연차 기록에서 연차 기록 카테고리중 부여만 해당하는 거 나타내주면 됨 */

    /* 4. 소진 연차 : 연차 기록에서 연차 기록 카테고리중 소진만 해당하는 거 나타내주면 됨 */

    /* 5.조정 정보 : 이거는 프론트에서 먼저 만들어야 하는데
    * 프론트에서 부여,소진 연차를 누르면 카테고리 설정이 되고
    * 조정 일수는 amount 컬럼에 숫자를 넣어줄거임
    *  1. 조정항목 : 버튼식이고 누르면 색이 바뀜
       2. 조정 일수 : 플마 눌러서 가능
    *  3. 합계 : 현재 상태(부여 연차, 소진연차)에서 계산된 값이 표시됨
       4. 저장 : 저장 버튼 누르면 팝업창이 뜨면서 저장이 완료됨*/

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 7.모든 사원 근태 현황(Attendance Status of All Employees) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 조건별 상세 검색
     * 1. 입사일을 기준으로 검색을 할거다.
     * 2. 사원 + 연차 테이블을 조인해서 나타내야한다.
     * 3. 근데 이거 전부 다 프론트라서.. 어카냐 지금..  */

    /* 2. 목록 : 이름이랑 직급이랑 같이 나오게 해야하는거 말고는 딱히 신경써야 할 부분이 없어보임 */

}
