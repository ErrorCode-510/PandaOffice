package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="EmployeePhoto")
@Table(name="employee_photo")
public class EmployeePhoto {
    @Id
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="name")
    private String name;
    @Column(name="path")
    private String path;
}
