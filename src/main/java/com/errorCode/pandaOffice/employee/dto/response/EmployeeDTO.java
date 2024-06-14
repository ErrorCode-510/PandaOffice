package com.errorCode.pandaOffice.employee.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;


public class EmployeeDTO {


    private int employeeId;


    private String name;

    private String englishName;

    private String hanjaName;

    private int departmentId;

    private int jobId;

    private String phone;

    private String personalId;

    private String gender;

    private Date hireDate;

    private Date endDate;

    private String address;

    private String nationality;

    private Date birthDate;

    private String email;

    private String selfIntroduction;

    private String employmentStatus;

    protected EmployeeDTO(){}

    public EmployeeDTO(int employeeId, String name, String englishName, String hanjaName, int departmentId, int jobId, String phone, String personalId, String gender, Date hireDate, Date endDate, String address, String nationality, Date birthDate, String email, String selfIntroduction, String employmentStatus) {
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