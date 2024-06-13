package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {

    @Id
    private int employeeId;
}
