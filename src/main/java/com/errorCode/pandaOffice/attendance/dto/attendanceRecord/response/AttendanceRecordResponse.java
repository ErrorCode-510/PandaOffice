package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordResponse {

    private List<CalculatedAttendanceRecord> calculatedAttendanceRecords;

    public static AttendanceRecordResponse of(List<AttendanceRecord> recordList) {
        AttendanceRecordResponse response = new AttendanceRecordResponse();
        response.calculatedAttendanceRecords = recordList.stream()
                .map(record -> CalculatedAttendanceRecord.of(record, recordList))
                .collect(Collectors.toList());

        return response;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class CalculatedAttendanceRecord {

        private String weeklyTotalTime;
        private String monthlyTotalTime;
        private String remainingTime;
        private Map<String, List<Map<String, String>>> weeklyStartEndTimes; // 새로운 필드 추가

        public static CalculatedAttendanceRecord of(AttendanceRecord record, List<AttendanceRecord> recordList) {

            Map<String, Duration> weeklyTotalTimes = calculateWeeklyTotalTimes(recordList);
            Map<String, Duration> monthlyTotalTimes = calculateMonthlyTotalTimes(recordList);
            Map<String, List<Map<String, String>>> weeklyStartEndTimes = calculateWeeklyStartEndTimes(recordList); // 새로운 계산 메서드 호출

            Duration fortyHours = Duration.ofHours(40);

            String week = getWeek(record.getDate());
            String month = record.getDate().getYear() + "-" + record.getDate().getMonthValue();

            Duration weeklyTotalTimeDuration = weeklyTotalTimes.getOrDefault(week, Duration.ZERO);
            Duration monthlyTotalTimeDuration = monthlyTotalTimes.getOrDefault(month, Duration.ZERO);

            Duration remainingTimeDuration = fortyHours.minus(weeklyTotalTimeDuration);

            String formattedWeeklyTotalTime = formatDuration(weeklyTotalTimeDuration);
            String formattedMonthlyTotalTime = formatDuration(monthlyTotalTimeDuration);
            String formattedRemainingTime = formatDuration(remainingTimeDuration);

            return new CalculatedAttendanceRecord(
                    formattedWeeklyTotalTime,
                    formattedMonthlyTotalTime,
                    formattedRemainingTime,
                    weeklyStartEndTimes
            );
        }

        private static String formatDuration(Duration duration) {
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return hours + "시간 " + minutes + "분";
        }

        public static String getWeek(LocalDate date) {
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
            int weekOfMonth = date.get(weekFields.weekOfMonth()) + 1;

            if (weekOfMonth == 0) {
                LocalDate lastDayOfLastMonth = date.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);
                return getWeek(lastDayOfLastMonth);
            }

            LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

            if (weekOfMonth == lastDayOfMonth.get(weekFields.weekOfMonth()) + 1 && lastDayOfMonth.getDayOfWeek().compareTo(DayOfWeek.THURSDAY) < 0) {
                LocalDate firstDayOfNextMonth = lastDayOfMonth.plusDays(1);
                return getWeek(firstDayOfNextMonth);
            }

            return date.getYear() + "-" + date.getMonthValue() + "-W" + weekOfMonth;
        }

        public static int getWeekOfMonth(LocalDate date) {
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
            return date.get(weekFields.weekOfMonth()) + 1;
        }

        public static Map<String, Duration> calculateWeeklyTotalTimes(List<AttendanceRecord> attendanceRecords) {
            Map<String, Duration> weeklyTotalTimes = new HashMap<>();

            for (AttendanceRecord attendanceRecord : attendanceRecords) {
                String week = getWeek(attendanceRecord.getDate());
                Duration workDuration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());
                weeklyTotalTimes.merge(week, workDuration, Duration::plus);
            }

            return weeklyTotalTimes;
        }

        public static Map<String, Duration> calculateMonthlyTotalTimes(List<AttendanceRecord> attendanceRecords) {
            Map<String, Duration> monthlyTotalTimes = new HashMap<>();

            for (AttendanceRecord attendanceRecord : attendanceRecords) {
                String month = attendanceRecord.getDate().getYear() + "-" + attendanceRecord.getDate().getMonthValue();
                Duration workDuration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());
                monthlyTotalTimes.merge(month, workDuration, Duration::plus);
            }

            return monthlyTotalTimes;
        }

        public static Map<String, List<Map<String, String>>> calculateWeeklyStartEndTimes(List<AttendanceRecord> attendanceRecords) {
            Map<String, List<Map<String, String>>> weeklyStartEndTimes = new HashMap<>();

            for (AttendanceRecord attendanceRecord : attendanceRecords) {
                String week = getWeek(attendanceRecord.getDate());
                int weekOfMonth = getWeekOfMonth(attendanceRecord.getDate());
                String weekKey = week + ": " + weekOfMonth;
                Map<String, String> recordMap = new HashMap<>();
                recordMap.put("week", String.valueOf(weekOfMonth));
                recordMap.put("date", String.valueOf(attendanceRecord.getDate().getDayOfMonth()));
                recordMap.put("day", attendanceRecord.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
                recordMap.put("start", attendanceRecord.getCheckInTime().toString());
                recordMap.put("end", attendanceRecord.getCheckOutTime().toString());
                Duration dayDuration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());
                recordMap.put("dayDuration", formatDuration(dayDuration));
                weeklyStartEndTimes.computeIfAbsent(weekKey, k -> new ArrayList<>()).add(recordMap);
            }

            return weeklyStartEndTimes;
        }
    }
}
