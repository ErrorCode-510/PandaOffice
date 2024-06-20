package com.errorCode.pandaOffice.recruitment.presectation;

import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import com.errorCode.pandaOffice.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /* 면접자 전체 및 검색 조회 */
    @GetMapping("/applicant")
    /* 엔티티 반환 값을 페이징으로 응답하겠다. */
    public ResponseEntity<PagingResponse> getAllApplicant(
            /* defaultValue: 요청 값으로 page 값이 넘어오지 않을 경우 디폴트 값 1 설정 */
            @RequestParam(defaultValue = "1") final Integer page,
            /* required = false: 파라미터로 반드시 넘어올 필요는 없다. 디폴트: true */
            @RequestParam(required = false) final String gender,
            @RequestParam(required = false) final String address,
            @RequestParam(required = false) final Integer age,
            @RequestParam(required = false) final String name
    ) {
        System.out.println(2142346567);
        /* 모든 데이터를 받아온 후 엔티티 타입을 DTO 타입으로 변환 후 받을거고, 페이징처리 하겠다. */
        final Page<ApplicantResponse> applicants = recruitmentService.getAllApplicant(page, gender, address, age, name);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(applicants);
        final PagingResponse pagingResponse = PagingResponse.of(applicants.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
}
