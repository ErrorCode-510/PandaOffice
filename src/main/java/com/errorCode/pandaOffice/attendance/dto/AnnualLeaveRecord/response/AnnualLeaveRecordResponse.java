package com.errorCode.pandaOffice.attendance.dto.AnnualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveRecordResponse {

    /* 2.내 연차 내역(Annual Leave Record)의 기능들 */

    /* 1.클래스 안에 클래스를 명시해준다.
    * 아래에 적힌 클래스 명들은 피그마 한 페이지 안에 들어가는 API 하나를 의미한다. */

    /* 1. 내 연차 내역 페이지에 쓸 클래스 */
    private CalculateLeaveRecord calculateLeaveRecord;

    /*2. */
    private List<UsedLeaveRecord> usedLeave;

    private List<CreatedRecord> createdRecord;

    private List<AnnualLeaveRecordCalendar> annualLeaveRecordCalendars;

    private List<AllLeaveRecord> allLeaveRecords;

    private List<GrantAndUsedLeave> grantAndUsedLeaves;


    /* 2.AnnualLeaveRecordResponse 타입의 of 메소드를 만들어 준다.
     * 1.이 메소드는 service 클래스에서 쓸 것이다.
     * 2.이 메소드는 AnnualLeaveRecord를 리스트로 형태로 받아들이고
     * 그걸 변환 해준뒤 response 형태로 내보내는 것이다. */
    public static AnnualLeaveRecordResponse of(List<AnnualLeaveRecord> recordList) {

        AnnualLeaveRecordResponse response = new AnnualLeaveRecordResponse();

        response.usedLeave = recordList.stream()
                .filter(record->record
                        .getAnnualLeaveCategory()   //카테고리 가져오기
                        .getType()                  // 타입명 가져오기
                        .equals("소진"))              // 비교 결과값 (필터 완료)
                .map(
                        /* 한번 더 of 메소드를 작성해서 내보낼 값을 설정해준다. */
                        recordEntity -> UsedLeaveRecord.of(recordEntity)
                )
                .toList();

        response.createdRecord = recordList.stream()
                .filter(record -> record
                        .getAnnualLeaveCategory()
                        .getType()
                        .equals("부여"))
                .map(
                        recordEntity -> CreatedRecord.of(recordEntity)
                )
                .toList();

        response.calculateLeaveRecord = CalculateLeaveRecord.of(recordList);

        response.annualLeaveRecordCalendars = recordList.stream()
                .filter(record->record
                        .getAnnualLeaveCategory()   //카테고리 가져오기
                        .getType()                  // 타입명 가져오기
                        .equals("소진"))              // 비교 결과값 (필터 완료)
                .map(
                        /* 한번 더 of 메소드를 작성해서 내보낼 값을 설정해준다. */
                        recordEntity -> AnnualLeaveRecordCalendar.of(recordEntity)
                )
                .toList();

        // 연차 기록을 사원 이름으로 그룹화한 후 AllLeaveRecord로 변환하여 설정합니다.
        response.allLeaveRecords = recordList.stream()
                // 1. recordList를 사원 이름으로 그룹화합니다.
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()))

                // 2. 그룹화된 값을 가져옵니다 (Map<String, List<AnnualLeaveRecord>>의 values를 스트림으로 변환).
                .values().stream()

                // 3. 각 그룹 (List<AnnualLeaveRecord>)을 AllLeaveRecord로 변환합니다.
                .map(AllLeaveRecord::of)

                // 4. 변환된 AllLeaveRecord 객체들을 List로 수집합니다.
                .collect(Collectors.toList());

        response.grantAndUsedLeaves = recordList.stream()
                // 1. recordList를 사원 이름으로 그룹화합니다.
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()))

                // 2. 그룹화된 값을 가져옵니다 (Map<String, List<AnnualLeaveRecord>>의 values를 스트림으로 변환).
                .values().stream()

                // 3. 각 그룹 (List<AnnualLeaveRecord>)을 GrantAndUsedLeave로 변환합니다.
                .map(GrantAndUsedLeave::of)

                // 4. 변환된 GrantAndUsedLeave 객체들을 List로 수집합니다.
                .collect(Collectors.toList());

        return response;
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1. 내 연차 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1-1.연차 계산 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    @Getter
    @RequiredArgsConstructor
    // 연차를 계산하기 위한 클래스를 따로 만들어 준다.
    public static class CalculateLeaveRecord{
        private double generatedLeave;
        private double adjustedLeave;
        private double totalLeave;
        private double usedLeave;
        private double remainingLeave;

        public static CalculateLeaveRecord of(List<AnnualLeaveRecord> recordList) {

            // 계산된 연차 내역을 위한 변수명들을 선언해준다.
            double generatedLeave = 0.0;
            double adjustedLeave = 0.0;
            double totalLeave = 0.0;
            double usedLeave = 0.0;

            int currentYear = LocalDate.now().getYear();

            for (AnnualLeaveRecord record : recordList) {
                String type = record.getAnnualLeaveCategory().getType();
                String name = record.getAnnualLeaveCategory().getName();
                LocalDate date = record.getDate();
                double amount = record.getAmount();

                if (date.getYear() == currentYear) {
                    if (name.equals("기본 발생")) {
                        generatedLeave += amount;
                    }

                    switch (type) {
                        case "부여":
                            adjustedLeave += amount;
                            totalLeave += amount;
                            break;
                        case "소진":
                            adjustedLeave -= amount;
                            usedLeave += amount;
                            break;
                        default:
                            // 기타 다른 타입이 있다면 무시
                            break;
                    }
                }
            }

            double remainingLeave = generatedLeave + adjustedLeave - usedLeave;

            CalculateLeaveRecord response = new CalculateLeaveRecord();
            response.generatedLeave = generatedLeave;
            response.adjustedLeave = adjustedLeave;
            response.totalLeave = totalLeave;
            response.usedLeave = usedLeave;
            response.remainingLeave = remainingLeave;

            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1-2.연차 사용 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    @Getter
    @RequiredArgsConstructor
    // 연차 사용 내역을 파악하기 위한 클래스를 따로 만들어준다.
    public static class UsedLeaveRecord{

        /* UsedLeaveRecord 에서 내보낼 필드명들을 적어준다. */
        private String employeeName;

        private String departmentName;

        private String categoryName;

        private LocalDate startDate;

        private LocalDate endDate;

        private double amount;

        public static UsedLeaveRecord of(AnnualLeaveRecord recordEntity) {

            UsedLeaveRecord response = new UsedLeaveRecord();

            response.employeeName = recordEntity.getEmployee().getName();
            response.departmentName = recordEntity.getEmployee().getDepartment().getName();
            response.categoryName = recordEntity.getAnnualLeaveCategory().getName();
            response.startDate = recordEntity.getDate();
            response.endDate = recordEntity.getDate().plusDays(
                    (long)Math.floor(recordEntity.getAmount())
            );
            response.amount = recordEntity.getAmount();
            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1-3.연차 생성 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    @Getter
    @RequiredArgsConstructor
    public static class CreatedRecord{

        private LocalDate registDate;

        private LocalDate validityDate;

        private String content;

        private double amount;

        public static CreatedRecord of(AnnualLeaveRecord recordEntity) {

            CreatedRecord response = new CreatedRecord();

            response.registDate = recordEntity.getDate();
            response.validityDate = recordEntity.getDate().plusYears(1);
            response.content = recordEntity.getContent();
            response.amount = recordEntity.getAmount();

            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 2.연차 캘린더 - 연차 기록 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    @Getter
    @RequiredArgsConstructor
    public static class AnnualLeaveRecordCalendar{

        private String employeeName;

        /* 사원의 직급 */
        private String employeeJob;

        private LocalDate startDate;

        private LocalDate endDate;

        private String annualLeaveType;

        public static AnnualLeaveRecordCalendar of(AnnualLeaveRecord recordEntity) {

            AnnualLeaveRecordCalendar response = new AnnualLeaveRecordCalendar();

            response.employeeName = recordEntity.getEmployee().getName();
            response.employeeJob = recordEntity.getEmployee().getJob().getTitle();
            response.startDate = recordEntity.getDate();
            response.endDate = recordEntity.getDate().plusDays((long) Math.floor(recordEntity.getAmount()));

            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 3.내 근태 신청 현황 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 4. 연차 조정 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 4-1. 사원들의 연차 정보 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    @Getter
    @RequiredArgsConstructor
    public static class AllLeaveRecord {

        private String departmentName;
        private String jobName;
        private String employeeName;
        private LocalDate hireDate;
        private int yearsOfService;

        private double totalGrantedLeave;
        private double totalUsedLeave;
        private double remainingLeave;

        private double defaultLeave;
        private double underOneYearLeave;
        private double rewardLeave;
        private double replaceLeave;

        private double defaultUse;
        private double underOneYearUse;
        private double rewardUse;
        private double replaceUse;

        public static AllLeaveRecord of(List<AnnualLeaveRecord> recordList) {
            AllLeaveRecord response = new AllLeaveRecord();

            if (!recordList.isEmpty()) {
                AnnualLeaveRecord firstRecord = recordList.get(0);
                LocalDate currentDate = LocalDate.now();

                response.departmentName = firstRecord.getEmployee().getDepartment().getName();
                response.jobName = firstRecord.getEmployee().getJob().getTitle();
                response.employeeName = firstRecord.getEmployee().getName();
                response.hireDate = firstRecord.getEmployee().getHireDate();
                response.yearsOfService = (int) ChronoUnit.YEARS.between(response.hireDate, currentDate);

                double defaultLeave = 0.0;
                double underOneYearLeave = 0.0;
                double rewardLeave = 0.0;
                double replaceLeave = 0.0;
                double defaultUse = 0.0;
                double underOneYearUse = 0.0;
                double rewardUse = 0.0;
                double replaceUse = 0.0;

                for (AnnualLeaveRecord record : recordList) {
                    switch (record.getAnnualLeaveCategory().getName()) {
                        case "기본발생":
                            defaultLeave += record.getAmount();
                            break;
                        case "1년미만":
                            underOneYearLeave += record.getAmount();
                            break;
                        case "보상":
                            rewardLeave += record.getAmount();
                            break;
                        case "대체":
                            replaceLeave += record.getAmount();
                            break;
                        case "기본사용":
                            defaultUse += record.getAmount();
                            break;
                        case "1년미만 사용":
                            underOneYearUse += record.getAmount();
                            break;
                        case "보상 사용":
                            rewardUse += record.getAmount();
                            break;
                        case "대체 사용":
                            replaceUse += record.getAmount();
                            break;
                    }
                }

                double totalLeave = defaultLeave + underOneYearLeave + rewardLeave + replaceLeave;
                double totalUse = defaultUse + underOneYearUse + rewardUse + replaceUse;
                double remainingLeave = totalLeave - totalUse;

                response.defaultLeave = defaultLeave;
                response.underOneYearLeave = underOneYearLeave;
                response.rewardLeave = rewardLeave;
                response.replaceLeave = replaceLeave;
                response.defaultUse = defaultUse;
                response.underOneYearUse = underOneYearUse;
                response.rewardUse = rewardUse;
                response.replaceUse = replaceUse;
                response.totalGrantedLeave = totalLeave;
                response.totalUsedLeave = totalUse;
                response.remainingLeave = remainingLeave;
            }

            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 4-2. 클릭한 사원의 부여, 소진 연차 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    @Getter
    @RequiredArgsConstructor
    public static class GrantAndUsedLeave {

        private double defaultLeave;
        private double underOneYearLeave;
        private double rewardLeave;
        private double replaceLeave;

        private double defaultUse;
        private double underOneYearUse;
        private double rewardUse;
        private double replaceUse;

        private LocalDate date;

        private LocalDate startDate;

        private LocalDate endDate;

        public static GrantAndUsedLeave of(List<AnnualLeaveRecord> recordList) {

            GrantAndUsedLeave response = new GrantAndUsedLeave();

            double defaultLeave = 0.0;
            double underOneYearLeave = 0.0;
            double rewardLeave = 0.0;
            double replaceLeave = 0.0;

            double defaultUse = 0.0;
            double underOneYearUse = 0.0;
            double rewardUse = 0.0;
            double replaceUse = 0.0;

            for (AnnualLeaveRecord record : recordList) {
                if (record.getAnnualLeaveCategory().getType().equals("부여")) {
                    switch (record.getAnnualLeaveCategory().getName()) {
                        case "기본발생":
                            defaultLeave += record.getAmount();
                            response.date = record.getDate();
                            break;
                        case "1년미만":
                            underOneYearLeave += record.getAmount();
                            response.date = record.getDate();
                            break;
                        case "보상":
                            rewardLeave += record.getAmount();
                            response.date = record.getDate();
                            break;
                        case "대체":
                            replaceLeave += record.getAmount();
                            response.date = record.getDate();
                            break;
                    }
                } else if (record.getAnnualLeaveCategory().getType().equals("소진")) {
                    switch (record.getAnnualLeaveCategory().getName()) {
                        case "기본사용":
                            defaultUse += record.getAmount();
                            response.date = record.getDate();
                            response.startDate = record.getDate();
                            response.endDate = record.getDate().plusDays(
                                    (long)Math.floor(record.getAmount()));
                            break;
                        case "1년미만":
                            underOneYearUse += record.getAmount();
                            response.startDate = record.getDate();
                            response.endDate = record.getDate().plusDays(
                                    (long)Math.floor(record.getAmount()));
                            break;
                        case "보상":
                            rewardUse += record.getAmount();
                            response.startDate = record.getDate();
                            response.endDate = record.getDate().plusDays(
                                    (long)Math.floor(record.getAmount()));
                            break;
                        case "대체":
                            replaceUse += record.getAmount();
                            response.startDate = record.getDate();
                            response.endDate = record.getDate().plusDays(
                                    (long)Math.floor(record.getAmount()));
                            break;
                    }
                }
            }

            response.defaultLeave = defaultLeave;
            response.underOneYearLeave = underOneYearLeave;
            response.rewardLeave = rewardLeave;
            response.replaceLeave = replaceLeave;

            response.defaultUse = defaultUse;
            response.underOneYearUse = underOneYearUse;
            response.rewardUse = rewardUse;
            response.replaceUse = replaceUse;

            return response;
        }
    }

}

//
//    /* 연차 기록 코드 */
//    private int id;
//
//    /* 연차 기록 변동일 */
//    private LocalDate date;
//
//    /* 잔여 연차 */
//    private double nowAmount;
//
//    /* 수량 (연차를 얼마나 썼는지, 받았는지에 대한 수량임) */
//    private double amount;
//
//    /* 연차에 대한 내용 */
//    private String content;
//
//    /* 발생 연차 */
//    private double generatedLeave;
//
//    /* 조정 연차 */
//    private double adjustedLeave;
//
//    /* 총 연차 */
//    private double totalLeave;
//
//    /* 사용 연차 */
//    private double usedLeave;
//
//    /* 잔여 연차 */
//    private double remainingLeave;
//
//    public AnnualLeaveRecordResponse(int id, LocalDate date, double nowAmount, double amount) {
//        this.id = id;
//        this.date = date;
//        this.nowAmount = nowAmount;
//        this.amount = amount;
//    }
//
//    public AnnualLeaveRecordResponse(double generatedLeave, double adjustedLeave, double totalLeave, double usedLeave, double remainingLeave) {
//        this.generatedLeave = generatedLeave;
//        this.adjustedLeave = adjustedLeave;
//        this.totalLeave = totalLeave;
//        this.usedLeave = usedLeave;
//        this.remainingLeave = remainingLeave;
//    }
