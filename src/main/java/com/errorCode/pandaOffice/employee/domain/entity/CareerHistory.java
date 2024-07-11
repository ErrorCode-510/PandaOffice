package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Entity(name="CareerHistory")
@Table(name="career_history")
@AllArgsConstructor

public class CareerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "department")
    private String department;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "last_position")
    private String lastPosition;
    @Column(name = "work_description")
    private String workDescription;

    protected CareerHistory() {
    }



    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}