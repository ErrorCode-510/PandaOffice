package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
@Getter
@Entity(name="FamilyName")
@Table(name="family_member")

public class FamilyMember {
    @Id
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="relationship")
    private String relationship;
    @Column(name="name")
    private String name;
    @Column(name="birth_date")
    private Date birthDate;
    @Column(name="education")
    private String education;
    @Column(name="job")
    private String job;
    protected FamilyMember(){}

    public FamilyMember(int id, Employee employee, String relationship, String name, Date birthDate, String education, String job) {
        this.id = id;
        this.employee = employee;
        this.relationship = relationship;
        this.name = name;
        this.birthDate = birthDate;
        this.education = education;
        this.job = job;
    }


}
