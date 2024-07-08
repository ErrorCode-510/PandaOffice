package com.errorCode.pandaOffice.attendance.dto.annualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnnualLeaveRecordCalendarsResponse {

    // 연차 캘린더 페이지 - 연차 캘린더의 연차 목록
    private List<AnnualLeaveRecordCalendar> annualLeaveRecordCalendars;

    public static AnnualLeaveRecordCalendarsResponse of(List<AnnualLeaveUsedRecord> usedRecordList) {
        AnnualLeaveRecordCalendarsResponse response = new AnnualLeaveRecordCalendarsResponse();
        // 연차 캘린더 페이지 - 사용된 연차 기록 리스트를 AnnualLeaveRecordCalendar 객체로 변환하여 설정
        response.annualLeaveRecordCalendars = usedRecordList.stream()
                .map(AnnualLeaveRecordCalendar::of)
                .collect(Collectors.toList());
        return response;
    }

    @Getter
    @ToString
    @NoArgsConstructor
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
            response.usedLeaveType = recordEntity.getAnnualLeaveGrantRecord().getAnnualLeaveCategory().getName();
            return response;
        }
    }
}
