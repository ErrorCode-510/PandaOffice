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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;

    private final OvertimeRecordRepository overtimeRecordRepository;

    private final AnnualLeaveRecordRepository annualLeaveRecordRepository;

    private final AnnualLeaveCategoryRepository annualLeaveCategoryRepository;

    /* 0. 현재 날짜의 주 계산식 메소드 */
    public void currentWeek() {

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 현재 날짜의 주를 가져오기 위해 WeekFields 설정 (한국 기준)
        WeekFields weekFields = WeekFields.of(Locale.KOREA);
        int weekNumber = currentDate.get(weekFields.weekOfMonth());

        // 결과 출력
        System.out.println("현재 날짜: " + currentDate);
        System.out.println("이번 달의 몇 번째 주인지: " + weekNumber);

    }

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
                        overtimeRecord.getStartDate(),
                        overtimeRecord.getEndDate(),
                        overtimeRecord.getType()
                ))
                .collect(Collectors.toList());
    }

    /* 3. 사원의 아이디를 기준으로 연차 기록을 가져온다. */
    public List<AnnualLeaveRecordResponse> getAnnualLeaveRecord(int employeeId) {
        List<AnnualLeaveRecord> annualLeaveRecords = annualLeaveRecordRepository.findByEmployee_EmployeeId(employeeId);

        return annualLeaveRecords.stream()
                .map(annualLeaveRecord -> new AnnualLeaveRecordResponse(
                        annualLeaveRecord.getId(),
                        annualLeaveRecord.getDate(),
                        annualLeaveRecord.getNowAmount(),
                        annualLeaveRecord.getAmount()
                ))
                .collect(Collectors.toList());
    }


    /* 4. 사원의 아이디를 기준으로 연차 기록 카테고리를 가져온다.
    * 연차 기록도 동시에 가져와야한다.*/
//    public List<AnnualLeaveCategoryResponse> getAnnualLeaveCategory(int employeeId) {
//        List<AnnualLeaveRecord> annualLeaveRecords = annualLeaveRecordRepository.findByEmployee_EmployeeId(employeeId);
//
//        List<AnnualLeaveCategory> annualLeaveCategories = annualLeaveRecords.findByAnnualLeaveCategory_
//
//
//    }





    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ1.내 근태 현황 페이지(Attendance Status)의 기능들 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    /* 1. 상단의 날짜를 프론트로부터 받아서 날짜에 따라 그 주에 해당하는 근태 기록들을 표로 확인하기 */

    /* 2. 상단의 표(사각형 5개)에 표시된 부분들 제작 */

    /* 3. 표에 보이는 부분들을 나타낼 수 있도록 제작 */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ2.내 연차 내역(Annual Leave Record)의 기능들*/

    /* 1. 사원의 연차를 표 형식으로 표현해줌  */

    /* 2. 연차 사용기간을 프론트에서 검색 받아서 사용 내역의 표가 변경된다.
    * 근데 사용 기간은 경우는 연차 기록 테이블에 있지 않기 때문에 검색하는 방법은
    * 등록일을 시작일로 받고 종료일을 등록일로부터 소진한 연차의 갯수만큼 더한 값을
    * 종료일로 해야한다.                                                    */

    /* 3. 생성 내역은 그냥 연차 기록에서 가져와서 값 나타내주면 됨
    * 유효기간 같은 경우는 등록일로부터 1년을 더하도록 로직을 짜면 되고
    * 윤년같은 경우는 366로 더하도록 로직을 짜야함                            */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ3.근태 캘린더(Attendance Calendar)  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 왼쪽 위에 화살표와 오늘 버튼 이거는 프론트쪽에서 작동하는 기능이지만
    * 화살표를 누르게 되면 비동기로 달력이 바뀌게 되는데
    * 달력이 바뀐 달에 맞춰서 달력을 바꿀거임
    * 즉 프론트에서 날짜가 바뀐다 -> 그 날짜를 rest에서 입력받는다 -> 입력받은 날짜에 맞춰서 값을 보내준다.
    * 연차의 값 중에서 소진한 연차의 값만 보내주면 된다. */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ4.내 근태 신청 현황(Attendance Input Status)ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 사원의 근태 신청 현황을 표 형식으로 보여줄건데
    *  1. 지각 : 근태 기록에서 지각한 날짜가 몇개인지 코드를 짜서 그 근태 번호에 모든 기록을 나타내도록 작성
        2. 연장 근무 : 연장 근무 테이블에서 연장 근무 타입에 대한 수를 종합하면 됨
        3. 휴일 근무 : 위랑 같음
        4. 연차 : 연차 테이블에서 소진 연차만 종합해서 하면 됨
        5. 총합은 위에 숫자들 다 더하면 됨 */

    /* 2.조회 기간 : 프론트에서 날짜 전달 받고 계산해서 날짜에 해당하는 애들 결과로 내줄거임 */

    /* 3.근태 신청 현황 : 근태 기록과 연장 근무 기록 테이블에서 따와서 보여주면 됨 */

    /* 4.연차 신청 현황 : 연차 기록 테이블에서 따와서 보여주면 됨 */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ5.근태 신청서(Attendance Form)ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* 1. 기안정보
    * 1. 기안자 : 사번을 통해서 사원 테이블의 정보를 출력
    * 2. 기안일 : 현재 시간
    * 3. 문서번호는 근태 친성서를 눌렀을 때 신규로 만들어진 문서번호를 받는다.*/

    /* 2.결재
    *  1. 똑같이 기안자에 대한 정보를 받아서 신청쪽에는 작성자 본인의 정보를
       2. 승인 파트는 고정이다. */

    /* 3. 신청 내용
    *  1. 근무 구분 : 버튼을 누르는 식으로 해서 버튼을 누르면 근태 신청의 카테고리가 정해진다.
       2. 근무 일시 : 본인이 직접 입력
       3. 근무시간 : 근무 일시를 알아서 계산함
       4. 비고는 알아서 적기
       5. 신청완료 버튼 누르면 submit으로 된다. 아마도? */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ6.연차 조정(Annual Leave Adjustment)ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

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

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ7.모든 사원 근태 현황(Attendance Status of All Employees)ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /* 1. 조건별 상세 검색
    * 1. 입사일을 기준으로 검색을 할거다.
    * 2. 사원 + 연차 테이블을 조인해서 나타내야한다.
    * 3. 근데 이거 전부 다 프론트라서.. 어카냐 지금..  */

    /* 2. 목록 : 이름이랑 직급이랑 같이 나오게 해야하는거 말고는 딱히 신경써야 할 부분이 없어보임 */

}
