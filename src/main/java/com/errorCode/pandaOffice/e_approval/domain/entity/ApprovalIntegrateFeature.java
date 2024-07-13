package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "approval_integrate_feature")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ApprovalIntegrateFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String path;
    private String tableBlock;
}
