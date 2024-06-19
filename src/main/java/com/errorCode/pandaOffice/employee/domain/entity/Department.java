package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="department")
@Getter
public class Department {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    protected Department(){}

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }



}
