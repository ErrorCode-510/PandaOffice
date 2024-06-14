package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name="License")
@Table(name="license")
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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
