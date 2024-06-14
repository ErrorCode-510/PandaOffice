package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;

@Entity(name="EducationHistory")
@Table(name="education_history")
public class EducationHistory {
    @Id
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="school_name")
    private String schoolName;
    @Column(name="major")
    private String major;
    @Column(name="degree")
    private String degree;
    protected EducationHistory(){}

    public EducationHistory(int id, Employee employee, String schoolName, String major, String degree) {
        this.id = id;
        this.employee = employee;
        this.schoolName = schoolName;
        this.major = major;
        this.degree = degree;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
