package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;

@Entity(name="Hobby")
@Table(name="hobby")
public class Hobby {
    @Id
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @Column(name="hobby")
    private String hobby;
    protected Hobby() {

    }

    public Hobby(int id, Employee employee, String hobby) {
        this.id = id;
        this.employee = employee;
        this.hobby = hobby;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
