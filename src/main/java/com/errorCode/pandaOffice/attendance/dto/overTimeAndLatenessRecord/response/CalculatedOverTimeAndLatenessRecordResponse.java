package com.errorCode.pandaOffice.attendance.dto.overTimeAndLatenessRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.OverTimeAndLatenessRecord;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedOverTimeAndLatenessRecordResponse {

    private List<CalculatedOverTimeAndLatenessRecord> calculatedOverTimeAndLatenessRecords;

    public static CalculatedOverTimeAndLatenessRecordResponse of(List<OverTimeAndLatenessRecord> recordList) {
        CalculatedOverTimeAndLatenessRecordResponse response = new CalculatedOverTimeAndLatenessRecordResponse();

        response.calculatedOverTimeAndLatenessRecords = recordList.stream()
                .map(record -> CalculatedOverTimeAndLatenessRecord.of(record, recordList))
                .collect(Collectors.toList());

        return response;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class CalculatedOverTimeAndLatenessRecord {

        private String weeklyOverTime;
        private String monthlyOverTime;

        public static CalculatedOverTimeAndLatenessRecord of(OverTimeAndLatenessRecord record, List<OverTimeAndLatenessRecord> recordList) {
            Map<String, Duration> weeklyOverTimes = calculateWeeklyOverTimes(recordList, record.getType());
            Map<String, Duration> monthlyOverTimes = calculateMonthlyOverTimes(recordList, record.getType());

            String week = getWeek(record.getDate());
            String month = record.getDate().getYear() + "-" + record.getDate().getMonthValue();

            Duration weeklyOverTimeDuration = weeklyOverTimes.getOrDefault(week, Duration.ZERO);
            Duration monthlyOverTimeDuration = monthlyOverTimes.getOrDefault(month, Duration.ZERO);

            String formattedWeeklyOverTime = formatDuration(weeklyOverTimeDuration);
            String formattedMonthlyOverTime = formatDuration(monthlyOverTimeDuration);

            return new CalculatedOverTimeAndLatenessRecord(
                    formattedWeeklyOverTime,
                    formattedMonthlyOverTime
            );
        }

        private static String formatDuration(Duration duration) {
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return hours + "시간 " + minutes + "분";
        }

        public static String getWeek(LocalDate date) {
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
            int weekOfMonth = date.get(weekFields.weekOfMonth());

            if (weekOfMonth == 0) {
                LocalDate lastDayOfLastMonth = date.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);
                return getWeek(lastDayOfLastMonth);
            }

            LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

            if (weekOfMonth == lastDayOfMonth.get(weekFields.weekOfMonth()) && lastDayOfMonth.getDayOfWeek().compareTo(DayOfWeek.THURSDAY) < 0) {
                LocalDate firstDayOfNextMonth = lastDayOfMonth.plusDays(1);
                return getWeek(firstDayOfNextMonth);
            }

            return date.getYear() + "-" + date.getMonthValue() + "-W" + weekOfMonth;
        }

        public static Map<String, Duration> calculateWeeklyOverTimes(List<OverTimeAndLatenessRecord> overTimeAndLatenessRecords, String type) {
            Map<String, Duration> weeklyOvertimes = new HashMap<>();

            for (OverTimeAndLatenessRecord overtimeAndLatenessRecord : overTimeAndLatenessRecords) {
                if (overtimeAndLatenessRecord.getType().equals(type)) {
                    String week = getWeek(overtimeAndLatenessRecord.getDate());
                    Duration overWorkDuration = Duration.between(overtimeAndLatenessRecord.getStartTime(), overtimeAndLatenessRecord.getEndTime());
                    weeklyOvertimes.merge(week, overWorkDuration, Duration::plus);
                }
            }

            return weeklyOvertimes;
        }

        public static Map<String, Duration> calculateMonthlyOverTimes(List<OverTimeAndLatenessRecord> overTimeAndLatenessRecords, String type) {
            Map<String, Duration> monthlyOverTimes = new HashMap<>();

            for (OverTimeAndLatenessRecord overtimeAndLatenessRecord : overTimeAndLatenessRecords) {
                if (overtimeAndLatenessRecord.getType().equals(type)) {
                    String month = overtimeAndLatenessRecord.getDate().getYear() + "-" + overtimeAndLatenessRecord.getDate().getMonthValue();
                    Duration overWorkDuration = Duration.between(overtimeAndLatenessRecord.getStartTime(), overtimeAndLatenessRecord.getEndTime());
                    monthlyOverTimes.merge(month, overWorkDuration, Duration::plus);
                }
            }

            return monthlyOverTimes;
        }
    }
}
