package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "No")
    private int no;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;          // 사번

    @Column(name = "bank")
    private String bank;                // 은행

    @Column(name = "account_number")
    private String accountNumber;       // 계좌번호

    public Account(String accountNumber, String bank) {
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public void setEmployee(Employee savedEmployee) {
        this.employee = savedEmployee;
    }

    public Account( Employee employee, String bank, String accountNumber) {

        this.employee = employee;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
