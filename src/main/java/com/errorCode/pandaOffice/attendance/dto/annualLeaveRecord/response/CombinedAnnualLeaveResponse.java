package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CombinedAnnualLeaveResponse {

    private CalculateAnnualLeaveRecordResponse calculateResponse;
    private AnnualLeaveUsedRecordResponse usedResponse;
    private AnnualLeaveGrantRecordResponse grantResponse;

}
