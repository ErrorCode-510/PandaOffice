package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
@Getter
@Entity(name="Employee")
@Table(name="employee")

public class Employee {

    @Id
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="password")
    private String password;

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
    private LocalDate hireDate;
    @Column(name="end_Date")
    private LocalDate endDate;
    @Column(name="address")
    private String address;
    @Column(name="nationality")
    private String nationality;
    @Column(name="birth_date")
    private LocalDate birthDate;
    @Column(name="email")
    private String email;
    @Column(name="self_introduction")
    private String selfIntroduction;
    @Column(name="employment_status")
    private String employmentStatus;
    /* 사원의 연봉 정보 추가 */
    @Column(name="annual_salary")
    private int annualSalary;
    private String refreshToken;
    public Employee(){}

    public Employee(int employeeId, String name, String englishName, String hanjaName, Department department, Job job, String phone, String personalId, String gender, LocalDate hireDate, LocalDate endDate, String address, String nationality, LocalDate birthDate, String email, String selfIntroduction, String employmentStatus, String password, int annualSalary) {
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
        this.password = password;
        this.annualSalary = annualSalary;
    }

    // New constructor with employeeId
    public Employee(int employeeId, String name, String englishName, String hanjaName, Department department, Job job, String phone, String personalId, String gender, LocalDate hireDate, String address, String nationality, LocalDate birthDate, String email, String employmentStatus) {
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
        this.address = address;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.email = email;
        this.employmentStatus = employmentStatus;
    }
    public void update(String name, String englishName, String hanjaName, Department department, Job job, String phone, String personalId, String gender, LocalDate hireDate, String address, String nationality, LocalDate birthDate, String email, String employmentStatus) {
        this.name = name;
        this.englishName = englishName;
        this.hanjaName = hanjaName;
        this.department = department;
        this.job = job;
        this.phone = phone;
        this.personalId = personalId;
        this.gender = gender;
        this.hireDate = hireDate;
        this.address = address;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.email = email;
        this.employmentStatus = employmentStatus;
    }

    // Method to change password
    public void changePassword(String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(newPassword);
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}