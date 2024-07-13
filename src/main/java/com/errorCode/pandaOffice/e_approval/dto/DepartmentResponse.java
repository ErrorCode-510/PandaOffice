package com.errorCode.pandaOffice.e_approval.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class DepartmentResponse {
    private int id;
    private String name;
}
