package com.errorCode.pandaOffice.employee.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ChangePasswordRequest {
    // Getters and setters (or constructor) for the fields
    private String email;
    private String newPassword;

}