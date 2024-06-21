package com.errorCode.devContents;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordMaker {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "비밀번호 입력칸";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
