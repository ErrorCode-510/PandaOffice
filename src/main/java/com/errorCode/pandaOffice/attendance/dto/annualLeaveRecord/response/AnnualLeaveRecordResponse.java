package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnnualLeaveRecordResponse {

    // 내 연차 내역 페이지 - 연차 계산 정보
    private CalculateLeaveRecord calculateLeaveRecord;

    // 내 연차 내역 페이지 - 사용된 연차 기록 목록
    private List<UsedLeaveRecord> usedLeave;

    // 내 연차 내역 페이지 - 부여된 연차 기록 목록
    private List<GrantedLeaveRecord> grantedLeave;

    // 연차 캘린더 페이지 - 연차 캘린더의 연차 목록
    private List<AnnualLeaveRecordCalendar> annualLeaveRecordCalendars;

    // 연차 조정 페이지 - 모든 연차 기록 목록
    private List<AllLeaveRecord> allLeaveRecords;

    // 연차 조정 페이지 - 부여 및 소진 연차 - 부여 및 사용된 연차 정보 목록
    private List<GrantAndUsedLeave> grantAndUsedLeaves;

    public static AnnualLeaveRecordResponse of(
            List<AnnualLeaveGrantRecord> grantRecordList,
            List<AnnualLeaveUsedRecord> usedRecordList) {

        AnnualLeaveRecordResponse response = new AnnualLeaveRecordResponse();

        // 내 연차 내역 페이지 - 연차 계산 정보를 설정
        response.calculateLeaveRecord = CalculateLeaveRecord.of(grantRecordList, usedRecordList);

        // 내 연차 내역 페이지 - 사용된 연차 기록 리스트를 UsedLeaveRecord 객체로 변환하여 설정
        response.usedLeave = usedRecordList.stream()
                .map(UsedLeaveRecord::of)
                .collect(Collectors.toList());

        // 내 연차 내역 페이지 - 부여된 연차 기록 리스트를 GrantedLeaveRecord 객체로 변환하여 설정
        response.grantedLeave = grantRecordList.stream()
                .map(GrantedLeaveRecord::of)
                .collect(Collectors.toList());

        // 연차 캘린더 페이지 - 사용된 연차 기록 리스트를 AnnualLeaveRecordCalendar 객체로 변환하여 설정
        response.annualLeaveRecordCalendars = usedRecordList.stream()
                .map(AnnualLeaveRecordCalendar::of)
                .collect(Collectors.toList());

        // 연차 조정 페이지 - 부여된 연차 기록 리스트를 직원 이름으로 그룹화하고 AllLeaveRecord 객체로 변환하여 설정
        response.allLeaveRecords = grantRecordList.stream()
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()))
                .values().stream()
                .map(groupedRecords -> AllLeaveRecord.of(groupedRecords, usedRecordList))
                .collect(Collectors.toList());

        // 연차 조정 페이지 - 부여된 연차 기록 리스트를 직원 이름으로 그룹화하고 GrantAndUsedLeave 객체로 변환하여 설정
        response.grantAndUsedLeaves = grantRecordList.stream()
                .collect(Collectors.groupingBy(record -> record.getEmployee().getName()))
                .values().stream()
                .map(groupedRecords -> GrantAndUsedLeave.of(groupedRecords, usedRecordList))
                .collect(Collectors.toList());

        return response;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CalculateLeaveRecord {

        // 기본 발생 연차
        private double defaultLeave;

        // 부여된 연차
        private double grantedLeave;

        // 총 연차
        private double totalLeave;

        // 사용된 연차
        private double usedLeave;

        // 남은 연차
        private double remainingLeave;

        public static CalculateLeaveRecord of(
                List<AnnualLeaveGrantRecord> grantRecordList,
                List<AnnualLeaveUsedRecord> usedRecordList) {

            double defaultLeave = 0.0;
            double grantedLeave = 0.0;
            double totalLeave = 0.0;
            double usedLeave = 0.0;

            int currentYear = LocalDate.now().getYear();
            int previousYear = currentYear - 1;

            boolean defaultLeaveCounted = false;

            for (AnnualLeaveGrantRecord record : grantRecordList) {
                LocalDate date = record.getDate();
                double amount = record.getAmount();

                if (!defaultLeaveCounted && (date.getYear() == previousYear || date.getYear() == currentYear) && record.getAnnualLeaveCategory().getName().equals("기본발생")) {
                    defaultLeave += amount;
                    totalLeave += amount;
                    defaultLeaveCounted = true;
                } else if (date.getYear() == currentYear) {
                    grantedLeave += amount;
                    totalLeave += amount;
                }
            }

            for (AnnualLeaveUsedRecord record : usedRecordList) {
                LocalDate date = record.getUsedStartDate();
                double amount = record.getUsedAmount();

                if (date.getYear() == currentYear) {
                    usedLeave += amount;
                }
            }

            double remainingLeave = totalLeave - usedLeave;

            CalculateLeaveRecord response = new CalculateLeaveRecord();
            response.defaultLeave = defaultLeave;
            response.grantedLeave = grantedLeave;
            response.totalLeave = totalLeave;
            response.usedLeave = usedLeave;
            response.remainingLeave = remainingLeave;

            return response;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class UsedLeaveRecord {

        private String employeeName;
        private String departmentName;
        private String leaveCategoryName;
        private LocalDate usedStartDate;
        private LocalDate usedEndDate;
        private double usedAmount;

        public static UsedLeaveRecord of(AnnualLeaveUsedRecord recordEntity) {
            UsedLeaveRecord response = new UsedLeaveRecord();
            response.employeeName = recordEntity.getEmployee().getName();
            response.departmentName = recordEntity.getEmployee().getDepartment().getName();
            response.leaveCategoryName = recordEntity.getAnnualLeaveCategory().getName();
            response.usedStartDate = recordEntity.getUsedStartDate();
            response.usedEndDate = recordEntity.getUsedEndDate();
            response.usedAmount = recordEntity.getUsedAmount();
            return response;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class GrantedLeaveRecord {

        private LocalDate grantedDate;
        private LocalDate expirationDate;
        private String content;
        private double grantedAmount;

        public static GrantedLeaveRecord of(AnnualLeaveGrantRecord recordEntity) {
            GrantedLeaveRecord response = new GrantedLeaveRecord();
            response.grantedDate = recordEntity.getDate();
            response.expirationDate = recordEntity.getExpirationDate();
            response.content = recordEntity.getAnnualLeaveCategory().getName();
            response.grantedAmount = recordEntity.getAmount();
            return response;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class AnnualLeaveRecordCalendar {

        private String employeeName;
        private String employeeJob;
        private LocalDate usedStartDate;
        private LocalDate usedEndDate;
        private String usedLeaveType;

        public static AnnualLeaveRecordCalendar of(AnnualLeaveUsedRecord recordEntity) {
            AnnualLeaveRecordCalendar response = new AnnualLeaveRecordCalendar();
            response.employeeName = recordEntity.getEmployee().getName();
            response.employeeJob = recordEntity.getEmployee().getJob().getTitle();
            response.usedStartDate = recordEntity.getUsedStartDate();
            response.usedEndDate = recordEntity.getUsedEndDate();
            response.usedLeaveType = recordEntity.getAnnualLeaveCategory().getName();
            return response;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class AllLeaveRecord {

        private String departmentName;
        private String jobName;
        private String employeeName;
        private LocalDate hireDate;
        private String yearsOfService;

        private double totalGrantedLeave;
        private double totalUsedLeave;
        private double remainingLeave;

        private double defaultLeave;
        private double underOneYearLeave;
        private double rewardLeave;
        private double replaceLeave;

        private double defaultUsed;
        private double underOneYearUsed;
        private double rewardUsed;
        private double replaceUsed;

        public static AllLeaveRecord of(List<AnnualLeaveGrantRecord> grantRecordList,
                                        List<AnnualLeaveUsedRecord> usedRecordList) {
            AllLeaveRecord response = new AllLeaveRecord();

            if (!grantRecordList.isEmpty()) {
                AnnualLeaveGrantRecord firstRecord = grantRecordList.get(0);
                LocalDate currentDate = LocalDate.now();

                response.departmentName = firstRecord.getEmployee().getDepartment().getName();
                response.jobName = firstRecord.getEmployee().getJob().getTitle();
                response.employeeName = firstRecord.getEmployee().getName();
                response.hireDate = firstRecord.getEmployee().getHireDate();
                response.yearsOfService = ChronoUnit.YEARS.between(response.hireDate, currentDate) + "년";

                double defaultLeave = 0.0;
                double underOneYearLeave = 0.0;
                double rewardLeave = 0.0;
                double replaceLeave = 0.0;
                double defaultUsed = 0.0;
                double underOneYearUsed = 0.0;
                double rewardUsed = 0.0;
                double replaceUsed = 0.0;

                for (AnnualLeaveGrantRecord record : grantRecordList) {
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
                    }
                }

                for (AnnualLeaveUsedRecord record : usedRecordList) {
                    switch (record.getAnnualLeaveCategory().getName()) {
                        case "기본발생":
                            defaultUsed += record.getUsedAmount();
                            break;
                        case "1년미만":
                            underOneYearUsed += record.getUsedAmount();
                            break;
                        case "보상":
                            rewardUsed += record.getUsedAmount();
                            break;
                        case "대체":
                            replaceUsed += record.getUsedAmount();
                            break;
                    }
                }

                double totalLeave = defaultLeave + underOneYearLeave + rewardLeave + replaceLeave;
                double totalUsedLeave = defaultUsed + underOneYearUsed + rewardUsed + replaceUsed;
                double remainingLeave = totalLeave - totalUsedLeave;

                response.defaultLeave = defaultLeave;
                response.underOneYearLeave = underOneYearLeave;
                response.rewardLeave = rewardLeave;
                response.replaceLeave = replaceLeave;

                response.defaultUsed = defaultUsed;
                response.underOneYearUsed = underOneYearUsed;
                response.rewardUsed = rewardUsed;
                response.replaceUsed = replaceUsed;

                response.totalGrantedLeave = totalLeave;
                response.totalUsedLeave = totalUsedLeave;
                response.remainingLeave = remainingLeave;
            }

            return response;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class GrantAndUsedLeave {

        private double defaultLeave;
        private double underOneYearLeave;
        private double rewardLeave;
        private double replaceLeave;

        private LocalDate date;
        private LocalDate approvalDate;
        private LocalDate usedStartDate;
        private LocalDate usedEndDate;
        private String usedLeaveName;
        private double usedAmount;

        public static GrantAndUsedLeave of(List<AnnualLeaveGrantRecord> grantRecordList, List<AnnualLeaveUsedRecord> usedRecordList) {
            GrantAndUsedLeave response = new GrantAndUsedLeave();

            double defaultLeave = 0.0;
            double underOneYearLeave = 0.0;
            double rewardLeave = 0.0;
            double replaceLeave = 0.0;

            String usedLeaveName = null;

            LocalDate approvalDate = null;
            LocalDate usedStartDate = null;
            LocalDate usedEndDate = null;

            double usedAmount = 0.0;

            for (AnnualLeaveGrantRecord record : grantRecordList) {
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
            }

            for (AnnualLeaveUsedRecord record : usedRecordList) {
                approvalDate = record.getApprovalDocument() != null ? record.getApprovalDocument().getApprovalDate() : null;
                usedStartDate = record.getUsedStartDate();
                usedEndDate = record.getUsedEndDate();
                usedLeaveName = record.getAnnualLeaveCategory().getName();
                usedAmount += record.getUsedAmount();
            }

            response.defaultLeave = defaultLeave;
            response.underOneYearLeave = underOneYearLeave;
            response.rewardLeave = rewardLeave;
            response.replaceLeave = replaceLeave;

            response.approvalDate = approvalDate;
            response.usedStartDate = usedStartDate;
            response.usedEndDate = usedEndDate;
            response.usedLeaveName = usedLeaveName;
            response.usedAmount = usedAmount;

            return response;
        }
    }
}
