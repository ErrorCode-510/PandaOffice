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

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;          // 사번

    @Column(name = "bank")
    private String bank;                // 은행

    @Column(name = "account_number")
    private String accountNumber;       // 계좌번호
}
