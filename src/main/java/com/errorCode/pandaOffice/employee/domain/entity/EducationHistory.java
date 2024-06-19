package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name="EducationHistory")
@Table(name="education_history")
@Getter
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


}
