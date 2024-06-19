package com.errorCode.pandaOffice.auth.dto;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.type.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

    private final Long memberCode;
    private final String memberId;
    private final String memberPassword;
    private final MemberRole memberRole;

    public static LoginDto from(Employee member) {
        return new LoginDto(
                member.getMemberCode(),
                member.getMemberId(),
                member.getMemberPassword(),
                member.getMemberRole()
        );
    }
}
