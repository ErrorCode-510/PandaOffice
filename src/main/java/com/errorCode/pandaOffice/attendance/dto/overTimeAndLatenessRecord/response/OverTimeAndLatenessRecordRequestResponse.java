package com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.OverTimeAndLatenessRecord;
import lombok.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OverTimeAndLatenessRecordRequestResponse {

    private List<OverTimeRecordDetail> overTimeRecords;
    private List<LateRecordDetail> lateRecords;

    public static OverTimeAndLatenessRecordRequestResponse of(List<OverTimeAndLatenessRecord> overTimeAndLatenessRecords) {

        List<OverTimeRecordDetail> overTimeRecordDetails = overTimeAndLatenessRecords.stream()
                .filter(record -> !record.getType().equals("지각"))
                .map(OverTimeRecordDetail::of)
                .collect(Collectors.toList());

        List<LateRecordDetail> lateRecordDetails = overTimeAndLatenessRecords.stream()
                .filter(record -> record.getType().equals("지각"))
                .map(LateRecordDetail::of)
                .collect(Collectors.toList());

        return new OverTimeAndLatenessRecordRequestResponse(overTimeRecordDetails, lateRecordDetails);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class OverTimeRecordDetail {

        private String type;
        private String dateRange;
        private String duration;
        private String approvalDate;
        private String status;

        public static OverTimeRecordDetail of(OverTimeAndLatenessRecord record) {
            if (record.getStartTime() == null || record.getEndTime() == null) {
                throw new IllegalArgumentException("Start time or end time is null for record: " + record);
            }

            String dateRange = "날짜 : " + record.getDate() + ", 시간: " + record.getStartTime() + " ~ " + record.getEndTime();
            Duration duration = Duration.between(record.getStartTime(), record.getEndTime());
            String formattedDuration = formatDuration(duration);

            String approvalDate = null;
            String status = null;
            if (record.getApprovalDocument() != null) {
                approvalDate = record.getApprovalDocument().getDraftDate().toString();
                status = record.getApprovalDocument().getStatus().toString();
            }

            return new OverTimeRecordDetail(
                    record.getType(),
                    dateRange,
                    formattedDuration,
                    approvalDate,
                    status
            );
        }

        private static String formatDuration(Duration duration) {
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return hours + "시간 " + minutes + "분";
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class LateRecordDetail {
        private final String date;
        private final String startTime;
        private final String endTime;
        private final String duration;
        private final String approvalDate;
        private final String status;

        public static LateRecordDetail of(OverTimeAndLatenessRecord record) {
            if (record.getStartTime() == null || record.getEndTime() == null) {
                throw new IllegalArgumentException("Start time or end time is null for record: " + record);
            }

            Duration duration = Duration.between(record.getStartTime(), record.getEndTime());
            String formattedDuration = formatDuration(duration);

            String approvalDate = null;
            String status = null;
            if (record.getApprovalDocument() != null) {
                approvalDate = record.getApprovalDocument().getDraftDate().toString();
                status = record.getApprovalDocument().getStatus().toString();
            }

            return new LateRecordDetail(
                    record.getDate().toString(),
                    record.getStartTime().toString(),
                    record.getEndTime().toString(),
                    formattedDuration,
                    approvalDate,
                    status
            );
        }

        private static String formatDuration(Duration duration) {
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return hours + "시간 " + minutes + "분";
        }
    }
}
