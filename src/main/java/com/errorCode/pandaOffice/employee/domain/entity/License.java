package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity(name="License")
@Table(name="license")
public class License {

    @Id
    @Column(name="id")
    private int id;
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="issuing_organization")
    private String issuingOrganization;
    @Column(name="issue_date")
    private Date issueDate;
    @Column(name="name")
    private String name;
    protected License(){}

    public License(int id, int employeeId, String issuingOrganization, Date issueDate, String name) {
        this.id = id;
        this.employeeId = employeeId;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
