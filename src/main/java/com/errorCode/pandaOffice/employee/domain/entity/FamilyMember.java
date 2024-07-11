package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Entity(name="FamilyName")
@Table(name="family_member")
@AllArgsConstructor
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate birthDate;
    @Column(name="education")
    private String education;
    @Column(name="job")
    private String job;
    protected FamilyMember(){}



    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
