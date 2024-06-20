package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

// 직급 엔티티
@Entity
@Table(name="job")
@Getter
public class Job {
    @Id
    @Column(name="id")
    private int id;  // 직급 코드

    @Column
    private String title;  // 직급명

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;  // employee 엔티티와 job 엔티티의 관계설정

    protected Job(){}

    public Job(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
