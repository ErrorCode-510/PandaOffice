package com.errorCode.pandaOffice.attendance.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.payroll.domain.entity.DeductionRecord;
import com.errorCode.pandaOffice.payroll.domain.entity.EarningRecord;
import com.errorCode.pandaOffice.payroll.domain.entity.PayrollRecord;
import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AnnualLeaveGrantRecord {

    // 인사팀에서 연차를 부여하는 것은 신청 서류가 필요없다.
    // 1년 미만 카테고리의 연차를 받으면 유효기간은 한달로 설정해야한다. - 연차 조정쪽에서 필요한 부분

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 부여 연차 기록 코드 */
    private int id;

    /* 부여 받은 수량 */
    private double amount;

    /* 부여 연차 수령일 */
    private LocalDate date;

    /* 부여 연차 유효기간 */
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "annual_leave_category_id")
    /* 연차 분류 코드 */
    private AnnualLeaveCategory annualLeaveCategory;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* 사번 */
    private Employee employee;



    


}
