package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
@Getter
@Entity(name="CareerHistory")
@Table(name="career_history")

public class CareerHistory {
    @Id
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
    private Date hireDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "last_position")
    private String lastPosition;
    @Column(name = "work_description")
    private String workDescription;

    protected CareerHistory() {
    }

    public CareerHistory(int id, Employee employee, String companyName, String department, Date hireDate, Date endDate, String lastPosition, String workDescription) {
        this.id = id;
        this.employee = employee;
        this.companyName = companyName;
        this.department = department;
        this.hireDate = hireDate;
        this.endDate = endDate;
        this.lastPosition = lastPosition;
        this.workDescription = workDescription;
    }

}