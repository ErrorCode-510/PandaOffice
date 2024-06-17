package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity(name="Employee")
@Table(name="employee")
@Getter
public class Employee {

    @Id
    @Column(name="employee_id")
    private int employeeId;

    @Column(name="name")
    private String name;
    @Column(name="english_name")
    private String englishName;
    @Column(name="hanja_name")
    private String hanjaName;
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;
    @Column(name="phone")
    private String phone;
    @Column(name = "personal_id")
    private String personalId;
    @Column(name="gender")
    private String gender;
    @Column(name="hire_date")
    private Date hireDate;
    @Column(name="end_Date")
    private Date endDate;
    @Column(name="address")
    private String address;
    @Column(name="nationality")
    private String nationality;
    @Column(name="birth_date")
    private Date birthDate;
    @Column(name="email")
    private String email;
    @Column(name="self_introduction")
    private String selfIntroduction;
    @Column(name="employment_status")
    private String employmentStatus;

    protected Employee(){}

    public Employee(int employeeId, String name, String englishName, String hanjaName, Department department, Job job, String phone, String personalId, String gender, Date hireDate, Date endDate, String address, String nationality, Date birthDate, String email, String selfIntroduction, String employmentStatus) {
        this.employeeId = employeeId;
        this.name = name;
        this.englishName = englishName;
        this.hanjaName = hanjaName;
        this.department = department;
        this.job = job;
        this.phone = phone;
        this.personalId = personalId;
        this.gender = gender;
        this.hireDate = hireDate;
        this.endDate = endDate;
        this.address = address;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.email = email;
        this.selfIntroduction = selfIntroduction;
        this.employmentStatus = employmentStatus;
    }


}