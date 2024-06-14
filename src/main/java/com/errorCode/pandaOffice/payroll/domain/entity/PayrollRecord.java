package com.errorCode.pandaOffice.payroll.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/* 급여 기록 Entity */
@Entity
@Table(name = "payroll_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PayrollRecord {

    /* 급여코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /* 사번 */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employeeId;

    /* 급여일 */
    @Column(name = "date")
    private LocalDate payrollDate;

    /* 급여명세서 경로 */
    @Column(name = "pay_stub_path")
    private String payStubPath;

}
