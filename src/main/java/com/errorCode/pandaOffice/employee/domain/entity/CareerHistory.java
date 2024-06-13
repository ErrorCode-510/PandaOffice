package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity(name="CareerHistory")
@Table(name="career_history")
public class CareerHistory {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="company_name")
    private String companyName;
    @Column(name="department")
    private String department;
    @Column(name="hire_date")
    private Date hireDate;
    @Column(name="end_date")
    private Date endDate;
    @Column(name="last_position")
    private String lastPosition;
    @Column(name="work_description")
    private String workDescription;
   protected CareerHistory(){}

    public CareerHistory(int id, int employeeId, String companyName, String department, Date hireDate, Date endDate, String lastPosition, String workDescription) {
        this.id = id;
        this.employeeId = employeeId;
        this.companyName = companyName;
        this.department = department;
        this.hireDate = hireDate;
        this.endDate = endDate;
        this.lastPosition = lastPosition;
        this.workDescription = workDescription;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(String lastPosition) {
        this.lastPosition = lastPosition;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }
}
