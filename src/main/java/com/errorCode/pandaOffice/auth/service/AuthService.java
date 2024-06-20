package com.errorCode.pandaOffice.auth.service;


import com.errorCode.pandaOffice.auth.dto.LoginDto;
import com.errorCode.pandaOffice.auth.dto.TokenDto;
import com.errorCode.pandaOffice.auth.type.CustomUser;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.employee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        LoginDto loginDto = memberService.findByMemberId(Integer.parseInt(employeeId));

        return User.builder()
                .username(String.valueOf(loginDto.getEmployeeId()))
                .password(loginDto.getPassword())

                .build();
    }

    public void updateRefreshToken(int employeeId, String refreshToken) {
        memberService.updateRefreshToken(employeeId, refreshToken);
    }

    public TokenDto checkRefreshTokenAndReIssueToken(String refreshToken) {

        LoginDto loginDto = memberService.findByRefreshToken(refreshToken);
        String reIssuedRefreshToken = TokenUtils.createRefreshToken();
        String reIssuedAccessToken = TokenUtils.createAccessToken(getMemberInfo(loginDto));
        memberService.updateRefreshToken(Integer.parseInt(String.valueOf(loginDto.getEmployeeId())), reIssuedRefreshToken);
        return TokenDto.of(reIssuedAccessToken, reIssuedRefreshToken);
    }

    private Map<String,Object> getMemberInfo(LoginDto loginDto) {
        return Map.of(
                "memberId", loginDto.getEmployeeId()

        );
    }

    public void saveAuthentication(int memberId) {

        LoginDto loginDto = memberService.findByMemberId(memberId);

        UserDetails user = User.builder()
            .username(String.valueOf(loginDto.getEmployeeId()))
            .password(loginDto.getPassword())

            .build();

        CustomUser customUser = new CustomUser(loginDto.getEmployeeId(), user);

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }








}
