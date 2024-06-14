package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;
import java.util.Date;

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
    private Date date;

    /* 근무 출근 시간 */
    private LocalTime checkInTime;

    /* 근무 퇴근 시간 */
    private LocalTime checkOutTime;

    /* 근무 누적 지각 시간 */
    private LocalTime totalLateTime;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;

    public AttendanceRecord(int id, Date date, LocalTime checkInTime,
                            LocalTime checkOutTime, LocalTime totalLateTime) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalLateTime = totalLateTime;
    }
}
