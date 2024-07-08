package com.errorCode.pandaOffice.employee.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private int employeeId;
    private String name;
    private String email;
    private String code;
}