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
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AllLeaveRecordsResponse {

    // 연차 조정 페이지 - 모든 연차 기록 목록
    private List<AllLeaveRecord> allLeaveRecords;

    public static AllLeaveRecordsResponse of(
            List<AnnualLeaveGrantRecord> grantRecordList,
            List<AnnualLeaveUsedRecord> usedRecordList) {

        AllLeaveRecordsResponse response = new AllLeaveRecordsResponse();

        // 연차 조정 페이지 - 소진된 연차 기록 리스트를 AllLeaveRecord 객체로 변환하여 설정
        response.allLeaveRecords = usedRecordList.stream()
                .map(usedRecord -> AllLeaveRecord.of(usedRecord, grantRecordList))
                .collect(Collectors.toList());

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

        public static AllLeaveRecord of(AnnualLeaveUsedRecord usedRecord,
                                        List<AnnualLeaveGrantRecord> grantRecordList) {

            AllLeaveRecord response = new AllLeaveRecord();

            response.departmentName = usedRecord.getEmployee().getDepartment().getName();
            response.jobName = usedRecord.getEmployee().getJob().getTitle();
            response.employeeName = usedRecord.getEmployee().getName();
            response.hireDate = usedRecord.getEmployee().getHireDate();
            response.yearsOfService = ChronoUnit.YEARS.between(response.hireDate, LocalDate.now()) + "년";

            double defaultGrant = 0.0;
            double underOneYearGrant = 0.0;
            double rewardGrant = 0.0;
            double replaceGrant = 0.0;
            double defaultUsed = 0.0;
            double underOneYearUsed = 0.0;
            double rewardUsed = 0.0;
            double replaceUsed = 0.0;

            for (AnnualLeaveGrantRecord record : grantRecordList) {
                if (record.getEmployee().getName().equals(usedRecord.getEmployee().getName())) {
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

            return response;
        }
    }
}
