package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="EducationHistory")
@Table(name="education_history")
public class EducationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "admission_date")
    private String admissionDate;

    @Column(name = "graduation_date")
    private String graduationDate;
    protected EducationHistory(){}

    public EducationHistory(int id, Employee employee, String schoolName, String major, String degree, String admissionDate, String graduationDate) {
        this.id = id;
        this.employee = employee;
        this.schoolName = schoolName;
        this.major = major;
        this.degree = degree;
        this.admissionDate = admissionDate;
        this.graduationDate = graduationDate;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
