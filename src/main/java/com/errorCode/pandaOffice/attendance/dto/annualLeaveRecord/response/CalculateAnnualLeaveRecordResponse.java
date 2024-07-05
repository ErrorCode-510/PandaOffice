package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class CalculateAnnualLeaveRecordResponse {

    private CalculateLeaveRecord calculateLeaveRecord;

    public static CalculateAnnualLeaveRecordResponse of(List<AnnualLeaveGrantRecord> grantRecordList,
                                                        List<AnnualLeaveUsedRecord> usedRecordList) {
        CalculateAnnualLeaveRecordResponse response = new CalculateAnnualLeaveRecordResponse();

        response.calculateLeaveRecord = CalculateLeaveRecord.of(grantRecordList, usedRecordList);

        return response;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CalculateLeaveRecord {
        private double defaultLeave;
        private double grantedLeave;
        private double totalLeave;
        private double usedLeave;
        private double remainingLeave;

        public static CalculateLeaveRecord of(List<AnnualLeaveGrantRecord> grantRecordList, List<AnnualLeaveUsedRecord> usedRecordList) {
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

                if (!defaultLeaveCounted && (date.getYear() == previousYear || date.getYear() == currentYear)
                        && record.getAnnualLeaveCategory().getName().equals("기본발생")) {

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
}
