package com.errorCode.pandaOffice.recruitment.service;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.domain.repository.ApplicantRepository;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentService {

    private final ApplicantRepository applicantRepository;

    /* 현재 페이지와 한 페이지당 보여줄 페이지 개수 */
    private Pageable getPageable(final Integer page) {
        /* 페이지 번호는 0부터 시작 */
        return PageRequest.of(page -1, 10);
    }

    /* 읽기전용, 조회만 가능, 값 변경 시 롤백 됨 */
    @Transactional(readOnly = true)
    public Page<ApplicantResponse> getAllApplicant(Integer page, String gender, String address, Integer age, String name) {

        /* 해당 조건문에서 나온 값 담기 */
        Page<Applicant> applicants = null;

        System.out.println("asdf" + applicantRepository.findAll());

        if (gender != null && !gender.isEmpty()) {
            /* applicant 엔티티에서 gender 필드를 사용해서 값 출력
             * 성별 + 이름으로 면접자 검색 */
            applicants = applicantRepository.findByApplicantGender(getPageable(page), gender, name);
        }
        else if (address != null && !address.isEmpty()) {

        } else if (age != null &&  age > 0) {

        }
        else {

        }

        /* 반환 된 값은 현재 엔티티이며, from(Mapper 대신 사용)을 사용해 DTO 타입으로 변경 */
        return applicants.map(ApplicantResponse::from);
    }
}
