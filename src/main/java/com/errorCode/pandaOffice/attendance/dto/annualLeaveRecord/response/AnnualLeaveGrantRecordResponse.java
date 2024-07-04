package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveGrantRecordResponse {

    private List<GrantedLeaveRecord> grantedLeaveRecords;

    public static AnnualLeaveGrantRecordResponse of(List<AnnualLeaveGrantRecord> grantRecords) {
        List<GrantedLeaveRecord> details = grantRecords.stream()
                .map(GrantedLeaveRecord::of)
                .collect(Collectors.toList());

        return new AnnualLeaveGrantRecordResponse(details);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class GrantedLeaveRecord {

        private LocalDate grantedDate;
        private LocalDate expirationDate;
        private double grantedAmount;
        private int id;
        private String annualLeaveCategory;
        private String employeeName;

        public static GrantedLeaveRecord of(AnnualLeaveGrantRecord grantRecord) {
            return new GrantedLeaveRecord(
                    grantRecord.getDate(),
                    grantRecord.getExpirationDate(),
                    grantRecord.getAmount(),
                    grantRecord.getId(),
                    grantRecord.getAnnualLeaveCategory().getName(),
                    grantRecord.getEmployee().getName()
            );
        }
    }
}
