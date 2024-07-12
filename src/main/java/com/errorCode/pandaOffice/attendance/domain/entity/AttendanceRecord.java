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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "employee_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
/* 근무 기록 */
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public static AttendanceRecord of(LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, Employee employee) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.date = date;
        attendanceRecord.checkInTime = checkInTime;
        attendanceRecord.checkOutTime = checkOutTime;
        attendanceRecord.employee = employee;
        return attendanceRecord;
    }

    // DTO를 통해 엔티티 수정
    public static AttendanceRecord updateCheckOutTime(AttendanceRecord existingRecord, LocalTime checkOutTime) {
        return new AttendanceRecord(existingRecord.id, existingRecord.date, existingRecord.checkInTime, checkOutTime, existingRecord.employee);
    }

    // 필요한 경우 모든 필드를 초기화하는 생성자 추가
    private AttendanceRecord(int id, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, Employee employee) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.employee = employee;
    }
}
