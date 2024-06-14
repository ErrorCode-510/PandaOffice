package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department_box")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 부서함 항목 */
public class DepartmentBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 부서함 이름 */
    private String name;
    /* 부서 ID */
    private int departmentId;
    /* 표시 순서 */
    @Column(name = "`order`", nullable = false)
    private int order;
}