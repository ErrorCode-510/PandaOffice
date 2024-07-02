package com.errorCode.pandaOffice.attendance.dto.annualLeaveUsedRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveUsedRecordResponse {

    private int id;
    private LocalDate usedStartDate;
    private LocalDate usedEndDate;
    private String leaveSession;
    private double usedAmount;
    private double remainingAmount;
    private String employeeName;
    private String annualLeaveCategory;
    private String annualLeaveGrantRecord;

    public static AnnualLeaveUsedRecordResponse of(AnnualLeaveUsedRecord usedRecord) {
        return new AnnualLeaveUsedRecordResponse(
                usedRecord.getId(),
                usedRecord.getUsedStartDate(),
                usedRecord.getUsedEndDate(),
                usedRecord.getLeaveSession(),
                usedRecord.getUsedAmount(),
                usedRecord.getRemainingAmount(),
                usedRecord.getEmployee().getName(),
                usedRecord.getAnnualLeaveCategory().getName(),
                usedRecord.getAnnualLeaveGrantRecord() != null ? String.valueOf(usedRecord.getAnnualLeaveGrantRecord().getId()) : null
        );
    }

}
