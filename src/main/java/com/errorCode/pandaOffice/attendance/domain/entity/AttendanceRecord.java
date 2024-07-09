package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
/* 근무 기록 */
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 근무 기록 코드 */
    private int id;

    /* 근무 기록 날짜 */
    private LocalDate date;

    /* 근무 출근 시간 */
    private LocalTime checkInTime;

    /* 근무 퇴근 시간 */
    private LocalTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;

    public static AttendanceRecord create(LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, Employee employee) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.date = date;
        attendanceRecord.checkInTime = checkInTime;
        attendanceRecord.checkOutTime = checkOutTime;
        attendanceRecord.employee = employee;
        return attendanceRecord;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        if (this.checkInTime != null) {
            throw new IllegalStateException("Check-in time is already set.");
        }
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        if (this.checkOutTime != null) {
            throw new IllegalStateException("Check-out time is already set.");
        }
        this.checkOutTime = checkOutTime;
    }

}
