package com.errorCode.pandaOffice.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name="job")
@EntityListeners(AuditingEntityListener.class)
public class Job {
    @Id
    @Column(name="id")
    private int id;
    @Column
    private String title;

    /* 직책수당 */
    @Column
    private int allowance;

    protected Job(){}

    public Job(int id, String title, int allowance) {
        this.id = id;
        this.title = title;
        this.allowance = allowance;
    }
}
