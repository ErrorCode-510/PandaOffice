package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity(name="EducationHistory")
@Table(name="education_history")
@AllArgsConstructor
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
    private LocalDate admissionDate;

    @Column(name = "graduation_date")
    private String graduationDate;
    protected EducationHistory(){}


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
