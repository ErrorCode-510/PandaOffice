package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


//설문 entity
//연결관계 어노테이션 설정 필요
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문id

    private int categoryId;// 카테고리id

    @Column(length = 255)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;


    public Survey(int id, int categoryId, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

