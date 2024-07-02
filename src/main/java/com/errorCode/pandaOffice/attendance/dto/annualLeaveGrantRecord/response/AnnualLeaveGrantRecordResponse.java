package com.errorCode.pandaOffice.attendance.dto.annualLeaveGrantRecord.response;


import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveGrantRecordResponse {

    private int id;
    private double amount;
    private LocalDate date;
    private LocalDate expirationDate;
    private String annualLeaveCategory;
    private String employeeName;

    public static AnnualLeaveGrantRecordResponse of(AnnualLeaveGrantRecord grantRecord) {
        return new AnnualLeaveGrantRecordResponse(
                grantRecord.getId(),
                grantRecord.getAmount(),
                grantRecord.getDate(),
                grantRecord.getExpirationDate(),
                grantRecord.getAnnualLeaveCategory().getName(),
                grantRecord.getEmployee().getName()
        );
    }

}
