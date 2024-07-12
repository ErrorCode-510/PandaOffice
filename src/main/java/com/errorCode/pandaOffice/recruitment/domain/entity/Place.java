package com.errorCode.pandaOffice.recruitment.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "place")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    /* 장소 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 장소 이름 */
    @Column(nullable = false, unique = true)
    private String name;
}