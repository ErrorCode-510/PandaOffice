package com.errorCode.pandaOffice.employee.service;


import com.errorCode.pandaOffice.auth.dto.LoginDto;
import com.errorCode.pandaOffice.auth.util.EmailUtils;
import com.errorCode.pandaOffice.common.exception.NotFoundException;
import com.errorCode.pandaOffice.employee.domain.entity.*;
import com.errorCode.pandaOffice.employee.domain.repository.*;
import com.errorCode.pandaOffice.employee.dto.request.AuthRequest;
import com.errorCode.pandaOffice.employee.dto.request.EmployeeDTO;
import com.errorCode.pandaOffice.employee.dto.response.ProfileResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private LicenseRepository licenseRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private EducationHistoryRepository educationHistoryRepository;
    @Autowired
    private EmployeePhotoRepository employeePhotoRepository;
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

        int hiredYear = employeeDTO.getEmployee().getHireDate().getYear();
        int depId = employeeDTO.getEmployee().getDepartment().getId();

// 전체 사원 수 조회
        Long totalEmployees = memberRepository.count();
        System.out.println(totalEmployees);

// 새 아이디 생성
        int newEmployeeId;
        if (totalEmployees != null && totalEmployees > 0) {
            newEmployeeId = Integer.parseInt(String.format("%04d%02d%03d", hiredYear, depId, totalEmployees + 1));
        } else {
            newEmployeeId = Integer.parseInt(String.format("%04d%02d001", hiredYear, depId));
        }


// 사원 객체에 아이디 설정
        Employee employee = employeeDTO.getEmployee();

        employee.formedEmployeeId(newEmployeeId);
        String defaultPwd = employee.getPhone().substring(employee.getPhone().length() - 4);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encodedPassword = encoder.encode(defaultPwd);
        employee.setDefaultPwd(encodedPassword);
        System.out.println(employee.getEmployeeId());
        Account emptyAccount =employeeDTO.getEmployee().getAccount();

//        System.out.println(employee.getAccount().getEmployee().getEmployeeId());
        Employee savedEmployee = memberRepository.save(employee);
        employeeDTO.setEmployee(savedEmployee);
        emptyAccount.setEmployee(savedEmployee);
        accountRepository.save(emptyAccount);
        System.out.println(employeeDTO.getPhotoPath());
        // Save photo
        EmployeePhoto employeePhoto = new EmployeePhoto(employeeDTO.getEmployee().getEmployeeId(), savedEmployee, employeeDTO.getPhotoName(), employeeDTO.getPhotoPath());
        System.out.println(employeePhoto.getPath());
        employeePhotoRepository.save(employeePhoto);
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
        List<License> licenses = employeeDTO.getLicenses();
        for (License license : licenses) {
            license.setEmployee(savedEmployee);
            licenseRepository.save(license);
        }

        System.out.println(employeeDTO.getEmployee().getEmployeeId()+"=========================================");



        return savedEmployee;
    }
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = memberRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployee(employee);
            System.out.println(employee.getAccount());
            // Fetch and set the photo
            Optional<EmployeePhoto> photoOptional = employeePhotoRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            if (photoOptional.isPresent()) {
                EmployeePhoto photo = photoOptional.get();
                employeeDTO.setPhotoName(photo.getName());
                employeeDTO.setPhotoPath(photo.getPath());
            }
            Optional<Account> accountOptional = accountRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            if (accountOptional.isPresent()) {
                System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
                Account account = accountOptional.get();
                employeeDTO.setAccount(account);

            }
            System.out.println(employeeDTO.getAccount().getAccountNumber());
            // Fetch and set family members
            List<FamilyMember> familyMembers = familyMemberRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            employeeDTO.setFamilyMember(familyMembers);

            // Fetch and set career history
            List<CareerHistory> careerHistories = careerHistoryRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            employeeDTO.setCareerHistory(careerHistories);

            // Fetch and set education history
            List<EducationHistory> educationHistories = educationHistoryRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            employeeDTO.setEducationHistory(educationHistories);
            List<License> licenses = licenseRepository.findByEmployeeEmployeeId(employee.getEmployeeId());
            employeeDTO.setLicenses(licenses);


            System.out.println(employeeDTO.getEmployee().getName()+"ddd");
            return employeeDTO;
        } else {
            throw new EntityNotFoundException("Employee not found");
        }
    }
    @Transactional
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        // 기존 사원 정보 조회
        Optional<Employee> optionalEmployee = memberRepository.findById((long) employeeDTO.getEmployee().getEmployeeId());
        if (!optionalEmployee.isPresent()) {

        }

        Employee existingEmployee = optionalEmployee.get();

        // 사원 기본 정보 업데이트
        existingEmployee.update(
                employeeDTO.getEmployee().getName(),
                employeeDTO.getEmployee().getEnglishName(),
                employeeDTO.getEmployee().getHanjaName(),
                employeeDTO.getEmployee().getDepartment(),
                employeeDTO.getEmployee().getJob(),
                employeeDTO.getEmployee().getPhone(),
                employeeDTO.getEmployee().getPersonalId(),
                employeeDTO.getEmployee().getGender(),
                employeeDTO.getEmployee().getHireDate(),
                employeeDTO.getEmployee().getAddress(),
                employeeDTO.getEmployee().getNationality(),
                employeeDTO.getEmployee().getBirthDate(),
                employeeDTO.getEmployee().getEmail(),
                employeeDTO.getEmployee().getEmploymentStatus(),
                employeeDTO.getEmployee().getAccount()
        );


         Employee savedEmployee = memberRepository.save(existingEmployee);

        // 사진 정보 업데이트
        EmployeePhoto employeePhoto = employeePhotoRepository.findByEmployeeEmployeeId(existingEmployee.getEmployeeId())
                .orElse(new EmployeePhoto(employeeDTO.getEmployee().getEmployeeId(), existingEmployee, employeeDTO.getPhotoName(), employeeDTO.getPhotoPath()));

        // Use encapsulation method to update the fields
        employeePhoto.updatePhotoDetails(employeeDTO.getPhotoName(), employeeDTO.getPhotoPath());

        // Save the updated entity
        employeePhotoRepository.save(employeePhoto);


        Account account = employeeDTO.getAccount();
        account.setEmployee(existingEmployee);
        accountRepository.save(account);
        // 가족 구성원 정보 업데이트
        List<FamilyMember> familyMembers = employeeDTO.getFamilyMember();
        familyMemberRepository.deleteByEmployeeEmployeeId(existingEmployee.getEmployeeId());
        for (FamilyMember familyMember : familyMembers) {
            familyMember.setEmployee(existingEmployee);
            familyMemberRepository.save(familyMember);
        }

        // 경력 정보 업데이트
        List<CareerHistory> careerHistories = employeeDTO.getCareerHistory();
        careerHistoryRepository.deleteByEmployeeEmployeeId(existingEmployee.getEmployeeId());
        for (CareerHistory careerHistory : careerHistories) {
            careerHistory.setEmployee(existingEmployee);
            careerHistoryRepository.save(careerHistory);
        }

        // 교육 정보 업데이트
        List<EducationHistory> educationHistories = employeeDTO.getEducationHistory();
        educationHistoryRepository.deleteByEmployeeEmployeeId(existingEmployee.getEmployeeId());
        for (EducationHistory educationHistory : educationHistories) {
            educationHistory.setEmployee(existingEmployee);
            educationHistoryRepository.save(educationHistory);
        }

        // 자격증 정보 업데이트
        List<License> licenses = employeeDTO.getLicenses();
        licenseRepository.deleteByEmployeeEmployeeId(existingEmployee.getEmployeeId());
        for (License license : licenses) {
            license.setEmployee(existingEmployee);
            licenseRepository.save(license);
        }

        return existingEmployee; // 업데이트된 객체 반환
    }

    }




