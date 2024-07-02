package com.errorCode.pandaOffice.employee.presentation;


import com.errorCode.pandaOffice.auth.service.AuthService;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.dto.request.AuthRequest;
import com.errorCode.pandaOffice.employee.dto.request.ChangePasswordRequest;
import com.errorCode.pandaOffice.employee.dto.request.EmployeeDTO;
import com.errorCode.pandaOffice.employee.dto.request.FindIdRequest;
import com.errorCode.pandaOffice.employee.dto.response.AuthResponse;
import com.errorCode.pandaOffice.employee.dto.response.ProfileResponse;
import com.errorCode.pandaOffice.employee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* 회원 가입 */
//    @PostMapping("/signup")
//    public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignupRequest memberRequest) {
//
//        memberService.signup(memberRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    /* 인증 테스트를 위한 메소드 */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test 응답 완료");
    }

    /* 본인 프로필 조회 */
    @GetMapping("/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.username")
//    @PostAuthorize("returnObject.writer == authentication.principal.username")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable int memberId) {

        ProfileResponse profileResponse = memberService.getProfile(Integer.parseInt(String.valueOf(memberId)));

        return ResponseEntity.ok(profileResponse);
    }

    /* 로그아웃 시 DB 토큰 무효화 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {

        try{
        memberService.updateRefreshToken(Integer.parseInt(userDetails.getUsername()), null);}
        catch (Exception e){
            System.out.println("Token is invalid or expired: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
    /* 아이디 찾기 */
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdRequest findIdRequest) {
        System.out.println("잘 돌아가고 있니");
        int memberId = memberService.findId(findIdRequest.getName(), findIdRequest.getEmail(), findIdRequest.getBirthDate());

        if (memberId != 0) {
            return ResponseEntity.ok(""+memberId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/send-auth-code")
    public ResponseEntity<AuthResponse> sendAuthCode(@RequestBody AuthRequest authRequest) {
        boolean result = memberService.verifyUserAndSendCode(authRequest);
        if (result) {
            return ResponseEntity.ok(new AuthResponse("Authentication code sent to email"));
        } else {
            return ResponseEntity.status(404).body(new AuthResponse("User not found"));
        }
    }
    @PostMapping("/verify-auth-code")
    public ResponseEntity<AuthResponse> verifyAuthCode(@RequestBody AuthRequest authRequest) {
        boolean result = memberService.verifyAuthCode(authRequest.getEmail(), authRequest.getCode());
        if (result) {
            return ResponseEntity.ok(new AuthResponse("Verification successful"));
        } else {

            return ResponseEntity.status(400).body(new AuthResponse("Invalid verification code"));

        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean result = memberService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword());

        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    // 모든 직원 조회
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = memberService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    @PostMapping("/newEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDTO employeeDTO) {

        System.out.println(employeeDTO.getLicenses());

        Employee savedEmployee = memberService.saveEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {

        EmployeeDTO employeeDTO = memberService.getEmployeeById(id);

        return ResponseEntity.ok(employeeDTO);
    }


}















