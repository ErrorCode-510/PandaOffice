package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="EducationHistory")
@Table(name="education_history")
public class EducationHistory {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="school_name")
    private String schoolName;
    @Column(name="major")
    private String major;
    @Column(name="degree")
    private String degree;
    protected EducationHistory(){}

    public EducationHistory(int id, int employeeId, String schoolName, String major, String degree) {
        this.id = id;
        this.employeeId = employeeId;
        this.schoolName = schoolName;
        this.major = major;
        this.degree = degree;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
