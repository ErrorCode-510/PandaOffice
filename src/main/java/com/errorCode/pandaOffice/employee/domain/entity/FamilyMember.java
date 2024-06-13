package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity(name="FamilyName")
@Table(name="family_member")
public class FamilyMember {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="relationship")
    private String relationship;
    @Column(name="name")
    private String name;
    @Column(name="birth_date")
    private Date birthDate;
    @Column(name="education")
    private String education;
    @Column(name="job")
    private String job;
    protected FamilyMember(){}

    public FamilyMember(int id, int employeeId, String relationship, String name, Date birthDate, String education, String job) {
        this.id = id;
        this.employeeId = employeeId;
        this.relationship = relationship;
        this.name = name;
        this.birthDate = birthDate;
        this.education = education;
        this.job = job;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
