package com.errorCode.pandaOffice.welfare.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//설문 카테고리(만족도조사)
//예를들어 동료, 시설, 부서 만족도 등..
@Entity
@Table(name="survey_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="category_name",length = 255)
    private String categoryName;


    public SurveyCategory(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
