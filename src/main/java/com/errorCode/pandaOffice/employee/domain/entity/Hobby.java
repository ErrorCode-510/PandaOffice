package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="Hobby")
@Table(name="hobby")

public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="hobby")
    private String hobby;
    protected Hobby() {

    }

    public Hobby(Employee employee, String hobby) {
        this.employee = employee;
        this.hobby = hobby;
    }




}
