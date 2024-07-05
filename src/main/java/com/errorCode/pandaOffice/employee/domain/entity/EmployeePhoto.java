package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name="EmployeePhoto")
@Table(name="employee_photo")

public class EmployeePhoto {
    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "path",columnDefinition = "LONGTEXT")
    private String path;

    public EmployeePhoto(int employeeId, Employee employee, String name, String path) {
        this.employeeId = employeeId;
        this.employee = employee;
        this.name = name;
        this.path = path;
    }

    public EmployeePhoto() {

    }


}
