package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;

@Entity(name="EmployeePhoto")
@Table(name="employee_photo")
public class EmployeePhoto {
    @Id
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="name")
    private String name;
    @Column(name="path")
    private String path;

    public EmployeePhoto(Employee employee, String name, String path) {
        this.employee = employee;
        this.name = name;
        this.path = path;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
