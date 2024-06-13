package com.errorCode.pandaOffice.welfare.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//설문 카테고리
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
// test-test-test
    @Id
    private int id;

    @Column(length = 255)
    private String categoryName;


    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
