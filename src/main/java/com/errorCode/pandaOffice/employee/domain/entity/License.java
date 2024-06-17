package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity(name="License")
@Table(name="license")
@Getter
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
    private Date issueDate;
    @Column(name="name")
    private String name;
    protected License(){}

}
