package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="Hobby")
@Table(name="hobby")
public class Hobby {
    @Id
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="hobby")
    private String hobby;
    protected Hobby() {

    }

    public Hobby(int id, Employee employee, String hobby) {
        this.id = id;
        this.employee = employee;
        this.hobby = hobby;
    }




}
