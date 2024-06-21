package com.errorCode.pandaOffice.employee.service;


import com.errorCode.pandaOffice.auth.dto.LoginDto;
import com.errorCode.pandaOffice.common.exception.NotFoundException;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.MemberRepository;
import com.errorCode.pandaOffice.employee.dto.request.MemberSignupRequest;
import com.errorCode.pandaOffice.employee.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.errorCode.pandaOffice.common.exception.type.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
//    public void signup(final MemberSignupRequest memberRequest) {
//
//        final Employee newEmployee = Employee.of(
//                memberRequest.getMemberId(),
//                passwordEncoder.encode(memberRequest.getMemberPassword()),
//                memberRequest.getMemberName(),
//                memberRequest.getMemberEmail()
//        );
//
//        memberRepository.save(newEmployee);
//    }

    @Transactional(readOnly = true)
    public LoginDto findByMemberId(int employeeId) {

        Employee employee = memberRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return LoginDto.from(employee);
    }

    public void updateRefreshToken(int employeeId, String refreshToken) {

        Employee employee = memberRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
        employee.updateRefreshToken(refreshToken);

    }

    @Transactional(readOnly = true)
    public LoginDto findByRefreshToken(String refreshToken) {
        Employee employee = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REFRESH_TOKEN));

        return LoginDto.from(employee);
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(int employeeId) {

        Employee employee = memberRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return ProfileResponse.from(employee);

    }
    @Transactional(readOnly = true)
    public int findId(String name, String email, LocalDate birthDate) {
        // 이름, 이메일, 생년월일을 기반으로 회원을 조회합니다.
        Employee employee = memberRepository.findByNameAndEmailAndBirthDate(name, email, birthDate)
                .orElse(null); // 수정: orElseThrow 대신에 orElse(null)을 사용합니다.
        System.out.println("실행되고 있니???");
        if (employee == null) {
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");
        }

        return employee.getEmployeeId(); // 회원 아이디를 반환합니다.
    }


}
