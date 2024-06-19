package com.errorCode.pandaOffice.auth.type;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
public class CustomUser extends User {
    private Long memberCode;
    public CustomUser(int memberCode, UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.memberCode = (long) memberCode;
    }
}
