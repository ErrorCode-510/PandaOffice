package com.errorCode.pandaOffice.recruitment.dto.response;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicantResponse {

    /* 면접자 코드 */
    private final Long id;

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

    /* applicant 엔티티를 dto 타입으로 변환 */
    public static ApplicantResponse from(final Applicant applicant) {
        return new ApplicantResponse(
                applicant.getId(),
                applicant.getName(),
                applicant.getBirthDate(),
                applicant.getGender(),
                applicant.getPhone(),
                applicant.getAddress(),
                applicant.getEmail()
        );
    }
}
