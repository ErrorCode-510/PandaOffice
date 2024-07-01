package com.errorCode.pandaOffice.employee.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationResponseDTO {

    private String employeeName;  // 사원 이름

    private String jobTitle;  // 직급

    private String departmentName;  // 부서

    private LocalDate birthDate;  // 생년월일

    private int age;  // 나이

    private String gender;  // 성별

    private String email;  // 이메일

    private List<String> hobby;  // 취미

    private String selfIntroduction;  // 자기소개

    private String employeeImage;  // 사원 이미지
}
