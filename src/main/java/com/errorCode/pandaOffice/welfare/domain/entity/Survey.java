package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


//설문 entity
//연결관계 어노테이션 설정 필요
@Entity
@Table(name="survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문id

    @Column(name = "category_id") //JoinColumn 사용해야 하지만 일단 보류
    private int categoryId;// 카테고리id

    @Column(name="title",length = 255)
    private String title;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;


    public Survey(int id, int categoryId, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

