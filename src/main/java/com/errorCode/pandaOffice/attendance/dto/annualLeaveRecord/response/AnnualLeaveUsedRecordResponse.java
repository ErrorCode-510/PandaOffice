package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveUsedRecordResponse {

    private List<AnnualLeaveUsedRecordDetail> annualLeaveUsedRecords;

    public static AnnualLeaveUsedRecordResponse of(List<AnnualLeaveUsedRecord> usedRecords) {
        List<AnnualLeaveUsedRecordDetail> details = usedRecords.stream()
                .map(AnnualLeaveUsedRecordDetail::of)
                .collect(Collectors.toList());

        return new AnnualLeaveUsedRecordResponse(details);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class AnnualLeaveUsedRecordDetail {

        private String employeeName;
        private String departmentName;
        private String usedType;
        private LocalDate usedStartDate;
        private LocalDate usedEndDate;
        private double usedAmount;
        private String leaveSession;
        private double remainingAmount;
        private String dateRange;
        private String duration;
        private String approvalDate;
        private String status;

        public static AnnualLeaveUsedRecordDetail of(AnnualLeaveUsedRecord usedRecord) {
            String dateRange = usedRecord.getUsedStartDate() + " ~ " + usedRecord.getUsedEndDate();
            Duration duration = Duration.between(usedRecord.getUsedStartDate().atStartOfDay(), usedRecord.getUsedEndDate().atStartOfDay());
            String formattedDuration = formatDuration(duration);

            String approvalDate = null;
            String status = null;
            if (usedRecord.getApprovalDocument() != null) {
                approvalDate = usedRecord.getApprovalDocument().getApprovalDate().toString();
                status = usedRecord.getApprovalDocument().getStatus().toString();
            }

            return new AnnualLeaveUsedRecordDetail(
                    usedRecord.getEmployee().getName(),
                    usedRecord.getEmployee().getDepartment().getName(),
                    usedRecord.getAnnualLeaveGrantRecord().getAnnualLeaveCategory().getName(),
                    usedRecord.getUsedStartDate(),
                    usedRecord.getUsedEndDate(),
                    usedRecord.getUsedAmount(),
                    usedRecord.getLeaveSession(),
                    usedRecord.getRemainingAmount(),
                    dateRange,
                    formattedDuration,
                    approvalDate,
                    status
            );
        }

        private static String formatDuration(Duration duration) {
            long days = duration.toDays();
            return days + "일";
        }
    }
}
