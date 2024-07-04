package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Entity(name="License")
@Table(name="license")
@AllArgsConstructor
public class License {

    @Id
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="issuing_organization")
    private String issuingOrganization;
    @Column(name="issue_date")
    private LocalDate issueDate;
    @Column(name="name")
    private String name;
    protected License(){}
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
