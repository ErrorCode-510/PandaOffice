package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity(name="Employee")
@Table(name="employee")
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
    @Column(name="department_id")
    private int departmentId;
    @Column(name = "job_id")
    private int jobId;
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

    public Employee(int employeeId, String name, String englishName, String hanjaName, int departmentId, int jobId, String phone, String personalId, String gender, Date hireDate, Date endDate, String address, String nationality, Date birthDate, String email, String selfIntroduction, String employmentStatus) {
        this.employeeId = employeeId;
        this.name = name;
        this.englishName = englishName;
        this.hanjaName = hanjaName;
        this.departmentId = departmentId;
        this.jobId = jobId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getHanjaName() {
        return hanjaName;
    }

    public void setHanjaName(String hanjaName) {
        this.hanjaName = hanjaName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
}