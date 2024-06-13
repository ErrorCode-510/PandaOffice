package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="Hobby")
@Table(name="hobby")
public class Hobby {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="hobby")
    private String hobby;
    protected Hobby() {

    }

    public Hobby(int id, int employeeId, String hobby) {
        this.id = id;
        this.employeeId = employeeId;
        this.hobby = hobby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
