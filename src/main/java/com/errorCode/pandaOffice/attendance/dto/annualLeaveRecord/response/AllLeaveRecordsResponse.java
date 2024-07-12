package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AllLeaveRecordsResponse {

    // 연차 조정 페이지 - 모든 연차 기록 목록
    private List<AllLeaveRecord> allLeaveRecords;

    // 전체 사원의 입사년도 중 최소값과 최대값
    private LocalDate minHireDate;
    private LocalDate maxHireDate;

    public static AllLeaveRecordsResponse of(
            List<AnnualLeaveGrantRecord> grantRecordList,
            List<AnnualLeaveUsedRecord> usedRecordList) {

        AllLeaveRecordsResponse response = new AllLeaveRecordsResponse();

        // 연차 부여 기록을 이름별로 그룹화
        Map<String, List<AnnualLeaveGrantRecord>> grantRecordMap = grantRecordList == null
                ? Collections.emptyMap()
                : grantRecordList.stream()
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()));

        // 연차 소진 기록을 이름별로 그룹화하고 AllLeaveRecord 객체로 변환하여 설정
        response.allLeaveRecords = usedRecordList.stream()
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()))
                .entrySet().stream()
                .map(entry -> AllLeaveRecord.of(entry.getValue(), grantRecordMap.get(entry.getKey())))
                .collect(Collectors.toList());

        // 전체 사원의 입사년도 중 최소값과 최대값 계산
        List<LocalDate> hireDates = usedRecordList.stream()
                .map(record -> record.getEmployee().getHireDate())
                .collect(Collectors.toList());

        if (!hireDates.isEmpty()) {
            response.minHireDate = hireDates.stream().min(Comparator.naturalOrder()).orElse(null);
            response.maxHireDate = hireDates.stream().max(Comparator.naturalOrder()).orElse(null);
        }

        return response;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllLeaveRecord {

        // 부서
        private String departmentName;
        // 직급
        private String jobName;
        // 사원명
        private String employeeName;
        // 입사일
        private LocalDate hireDate;
        // 근속년도
        private String yearsOfService;

        // 부여 연차 합계
        private double totalGrantedLeave;
        // 소진 연차 합계
        private double totalUsedLeave;
        // 잔여 연차
        private double remainingLeave;

        // 부여 - 기본 발생
        private double defaultGrant;
        // 부여 - 1년 미만
        private double underOneYearGrant;
        // 부여 - 보상
        private double rewardGrant;
        // 부여 - 대체
        private double replaceGrant;

        // 소진 - 기본 발생
        private double defaultUsed;
        // 소진 - 1년 미만
        private double underOneYearUsed;
        // 소진 - 보상
        private double rewardUsed;
        // 소진 - 대체
        private double replaceUsed;

        /* 1. 소진 연차 상세 정보 */
        private List<UsedLeaveDetail> usedLeaveDetails;

        /* 2. 부여 연차 상세 정보 */
        private List<GrantedLeaveDetail> grantedLeaveDetails;

        public static AllLeaveRecord of(List<AnnualLeaveUsedRecord> usedRecords,
                                        List<AnnualLeaveGrantRecord> grantRecords) {

            AllLeaveRecord response = new AllLeaveRecord();

            AnnualLeaveUsedRecord sampleRecord = usedRecords.get(0);

            response.departmentName = sampleRecord.getEmployee().getDepartment().getName();
            response.jobName = sampleRecord.getEmployee().getJob().getTitle();
            response.employeeName = sampleRecord.getEmployee().getName();
            response.hireDate = sampleRecord.getEmployee().getHireDate();

            long years = ChronoUnit.YEARS.between(response.hireDate, LocalDate.now());
            response.yearsOfService = (years < 1) ? "1년 미만" : years + "년";

            double defaultGrant = 0.0;
            double underOneYearGrant = 0.0;
            double rewardGrant = 0.0;
            double replaceGrant = 0.0;
            double defaultUsed = 0.0;
            double underOneYearUsed = 0.0;
            double rewardUsed = 0.0;
            double replaceUsed = 0.0;

            if (grantRecords != null) {
                for (AnnualLeaveGrantRecord record : grantRecords) {
                    switch (record.getAnnualLeaveCategory().getName()) {
                        case "기본발생":
                            defaultGrant += record.getAmount();
                            break;
                        case "1년미만":
                            underOneYearGrant += record.getAmount();
                            break;
                        case "보상":
                            rewardGrant += record.getAmount();
                            break;
                        case "대체":
                            replaceGrant += record.getAmount();
                            break;
                    }
                }
            }

            for (AnnualLeaveUsedRecord usedRecord : usedRecords) {
                switch (usedRecord.getAnnualLeaveGrantRecord().getAnnualLeaveCategory().getName()) {
                    case "기본발생":
                        defaultUsed += usedRecord.getUsedAmount();
                        break;
                    case "1년미만":
                        underOneYearUsed += usedRecord.getUsedAmount();
                        break;
                    case "보상":
                        rewardUsed += usedRecord.getUsedAmount();
                        break;
                    case "대체":
                        replaceUsed += usedRecord.getUsedAmount();
                        break;
                }
            }

            double totalGrant = defaultGrant + underOneYearGrant + rewardGrant + replaceGrant;
            double totalUsed = defaultUsed + underOneYearUsed + rewardUsed + replaceUsed;
            double remainingLeave = totalGrant - totalUsed;

            response.defaultGrant = defaultGrant;
            response.underOneYearGrant = underOneYearGrant;
            response.rewardGrant = rewardGrant;
            response.replaceGrant = replaceGrant;

            response.defaultUsed = defaultUsed;
            response.underOneYearUsed = underOneYearUsed;
            response.rewardUsed = rewardUsed;
            response.replaceUsed = replaceUsed;

            response.totalGrantedLeave = totalGrant;
            response.totalUsedLeave = totalUsed;
            response.remainingLeave = remainingLeave;

            response.usedLeaveDetails = usedRecords.stream()
                    .map(UsedLeaveDetail::of)
                    .collect(Collectors.toList());

            response.grantedLeaveDetails = grantRecords == null ? Collections.emptyList() : grantRecords.stream()
                    .map(GrantedLeaveDetail::of)
                    .collect(Collectors.toList());

            return response;
        }

        @Getter
        @RequiredArgsConstructor
        public static class UsedLeaveDetail {
            private final String usedType;
            private final LocalDate usedStartDate;
            private final LocalDate usedEndDate;
            private final double usedAmount;
            private final String leaveSession;

            public static UsedLeaveDetail of(AnnualLeaveUsedRecord usedRecord) {
                return new UsedLeaveDetail(
                        usedRecord.getAnnualLeaveGrantRecord().getAnnualLeaveCategory().getName(),
                        usedRecord.getUsedStartDate(),
                        usedRecord.getUsedEndDate(),
                        usedRecord.getUsedAmount(),
                        usedRecord.getLeaveSession()
                );
            }
        }

        @Getter
        @RequiredArgsConstructor
        public static class GrantedLeaveDetail {
            private final double amount;
            private final LocalDate date;
            private final String grantCategory;

            public static GrantedLeaveDetail of(AnnualLeaveGrantRecord grantRecord) {

                return new GrantedLeaveDetail(
                        grantRecord.getAmount(),
                        grantRecord.getDate(),
                        grantRecord.getAnnualLeaveCategory().getName()
                );
            }
        }
    }
}
