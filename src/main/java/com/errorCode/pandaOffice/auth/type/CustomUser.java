package com.errorCode.pandaOffice.auth.type;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
public class CustomUser extends User {
    private int employeeId;
    public CustomUser(int employeeId, UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.employeeId = employeeId;
    }
}
