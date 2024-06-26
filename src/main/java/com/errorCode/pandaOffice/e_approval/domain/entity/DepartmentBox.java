package com.errorCode.pandaOffice.e_approval.domain.entity;

import com.errorCode.pandaOffice.auth.dto.LoginDto;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "department_box")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 부서함 항목 */
public class DepartmentBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 부서함 이름 */
    private String name;
    /* 부서 */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    /* 표시 순서 */
    @Column(name = "`order`", nullable = false)
    private int order;
    /* 최종 수정자 */
    @ManyToOne
    @JoinColumn(name = "last_editor_id")
    private Employee lastEditor;
    /* 최근 수정일 */
    private LocalDate lastEditDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_box_id")
    private List<DepartmentDocument> documentList;
}