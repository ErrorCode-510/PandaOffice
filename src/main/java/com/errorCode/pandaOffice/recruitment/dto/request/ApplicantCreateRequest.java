package com.errorCode.pandaOffice.recruitment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ApplicantCreateRequest {

    /* 면접자 코드 */
    private final int id;

    /* 면접자 이름 */
    private final String name;

    /* 면접자 생년월일 */
    private final LocalDate birthDate;

    /* 면접자 성별 */
    private final String gender;

    /* 면접자 연락처 */
    private final String phone;

    /* 면접자 주소 */
    private final String address;

    /* 면접자 이메일 */
    private final String email;
}
