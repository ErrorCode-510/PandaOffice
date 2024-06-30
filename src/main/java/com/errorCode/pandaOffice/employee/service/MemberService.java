package com.errorCode.pandaOffice.employee.service;


import com.errorCode.pandaOffice.auth.dto.LoginDto;
import com.errorCode.pandaOffice.auth.util.EmailUtils;
import com.errorCode.pandaOffice.common.exception.NotFoundException;
import com.errorCode.pandaOffice.employee.domain.entity.CareerHistory;
import com.errorCode.pandaOffice.employee.domain.entity.EducationHistory;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.FamilyMember;
import com.errorCode.pandaOffice.employee.domain.repository.CareerHistoryRepository;
import com.errorCode.pandaOffice.employee.domain.repository.EducationHistoryRepository;
import com.errorCode.pandaOffice.employee.domain.repository.FamilyMemberRepository;
import com.errorCode.pandaOffice.employee.domain.repository.MemberRepository;
import com.errorCode.pandaOffice.employee.dto.request.AuthRequest;
import com.errorCode.pandaOffice.employee.dto.request.EmployeeDTO;
import com.errorCode.pandaOffice.employee.dto.response.ProfileResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.errorCode.pandaOffice.common.exception.type.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private CareerHistoryRepository careerHistoryRepository;

    @Autowired
    private EducationHistoryRepository educationHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtils emailUtils;
    private final Map<String, String> authCodeStore = new HashMap<>(); // In-memory store for auth codes

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
    public boolean verifyUserAndSendCode(AuthRequest authRequest) {
        System.out.println(authRequest.getEmployeeId());
        System.out.println(authRequest.getEmail());
        System.out.println(authRequest.getName());
        Optional<Employee> userOptional = memberRepository.findByEmployeeIdAndNameAndEmail(
                authRequest.getEmployeeId(),
                authRequest.getName(),
                authRequest.getEmail()
        );
        if (userOptional.isPresent()) {
            String code = emailUtils.generateAuthCode();
            authCodeStore.put(authRequest.getEmail(), code);
            emailUtils.sendEmailWithCode(authRequest.getEmail(), code);
            return true;
        } else {
            return false;
        }
    }
    public boolean verifyAuthCode(String email, String code) {
        String storedCode = authCodeStore.get(email);
        return storedCode != null && storedCode.equals(code);
    }
    public boolean changePassword(String email, String newPassword) {
        Optional<Employee> optionalEmployee = memberRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.changePassword(newPassword);
            memberRepository.save(employee); // 변경 사항을 데이터베이스에 저장
            return true;
        } else {
            throw new EntityNotFoundException("Employee with email " + email + " not found");
        }
    }
    public List<Employee> getAllEmployees() {
        return memberRepository.findAll();
    }
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.getEmployee();
        Employee savedEmployee = memberRepository.save(employee);

        // Save family members
        List<FamilyMember> familyMembers = employeeDTO.getFamilyMember();
        for (FamilyMember familyMember : familyMembers) {
            familyMember.setEmployee(savedEmployee);
            familyMemberRepository.save(familyMember);
        }

        // Save career history
        List<CareerHistory> careerHistories = employeeDTO.getCareerHistory();
        for (CareerHistory careerHistory : careerHistories) {
            careerHistory.setEmployee(savedEmployee);
            careerHistoryRepository.save(careerHistory);
        }

        // Save education history
        List<EducationHistory> educationHistories = employeeDTO.getEducationHistory();
        for (EducationHistory educationHistory : educationHistories) {
            educationHistory.setEmployee(savedEmployee);
            educationHistoryRepository.save(educationHistory);
        }

        return savedEmployee;
    }
    }




