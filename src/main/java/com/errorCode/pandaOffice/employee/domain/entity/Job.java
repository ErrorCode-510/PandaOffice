package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="job")
@Getter
public class Job {
    @Id
    @Column(name="id")
    private int id;
    @Column
    private String title;

    protected Job(){}

    public Job(int id, String title) {
        this.id = id;
        this.title = title;
    }


}
