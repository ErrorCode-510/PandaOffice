package com.errorCode.pandaOffice.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
/* 연차 분류 */
public class AnnualLeaveCategory {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 연차 분류 코드 */
    private int id;

    /* 연차 분류 이름 */
    private String name;

    /* 연자 분류 변동 수량 */
    private double amount;

    public AnnualLeaveCategory(int id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
