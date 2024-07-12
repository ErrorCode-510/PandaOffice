package com.errorCode.pandaOffice.recruitment.presectation;

import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.recruitment.dto.request.ApplicantRequest;
import com.errorCode.pandaOffice.recruitment.dto.request.InterviewScheduleCreateRequest;
import com.errorCode.pandaOffice.recruitment.dto.request.InterviewScheduleModifyRequest;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.InterviewScheduleResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.PlaceResponse;
import com.errorCode.pandaOffice.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /* 1. 면접자 전체 조회 */
    @GetMapping("/applicant")
    public ResponseEntity<PagingResponse> getApplicants(
            @RequestParam(defaultValue = "1") final Integer page
    ) {
        final Page<ApplicantResponse> applicantResponses = recruitmentService.getAllApplicant(page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(applicantResponses);
        final PagingResponse pagingResponse = PagingResponse.of(applicantResponses.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /* 2. 면접자 검색 조회 */
    @GetMapping("/applicant/search")
    /* 엔티티 반환 값을 페이징으로 응답하겠다. */
    public ResponseEntity<PagingResponse> getSearchApplicant(
            /* defaultValue: 요청 값으로 page 값이 넘어오지 않을 경우 디폴트 값 1 설정 */
            @RequestParam(defaultValue = "1") final Integer page,
            /* required = false: 파라미터로 반드시 넘어올 필요는 없다. 디폴트: true */
            /* 성별 검색 */
            @RequestParam(required = false) final String gender,
            /* 주소 검색 */
            @RequestParam(required = false) final String address,
            /* 이름 검색( 이름은 있을 수 있고 없을 수 있음 ) */
            @RequestParam(required = false) final String name
    ) {
        /* 모든 데이터를 받아온 후 엔티티 타입을 DTO 타입으로 변환 후 받을거고, 페이징처리 하겠다. */
        final Page<ApplicantResponse> applicants = recruitmentService.getSearchApplicant(page, gender, address, name);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(applicants);
        final PagingResponse pagingResponse = PagingResponse.of(applicants.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /* 3. 면접자 등록 */
    @PostMapping("/applicant/regist")
    public ResponseEntity<Void> registApplicant(
            @RequestBody ApplicantRequest applicantRequest
    ) {
        final Integer applicantId = recruitmentService.registApplicant(applicantRequest);
        return ResponseEntity.created(URI.create("recruitment/applicant/" + applicantId)).build();
    }

    /* 4. 면접자 상세 조회 */
    @GetMapping("/applicant/detail/{id}")
    public ResponseEntity<ApplicantResponse> detailApplicant(@PathVariable Integer id) {
        ApplicantResponse applicantResponse = recruitmentService.getApplicantById(id);

        /* 해당 ID의 면접자가 없을 경우 404 에러 반환 */
        if (applicantResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicantResponse);
    }

    /* 5. 면접자 수정 */
    @PutMapping("applicant/modify/{id}")
    public ResponseEntity<Void> applicantUpdate(
            @PathVariable Integer id,
            @RequestBody ApplicantRequest applicantRequest
    ) {
        recruitmentService.modify(id, applicantRequest);
        return ResponseEntity.created(URI.create("recruitment/applicant/" + id)).build();
    }

    /* 6. 면접자 삭제 */
    @DeleteMapping("applicant/delete/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Integer id) {
        recruitmentService.deleteApplicant(id);
        return ResponseEntity.noContent().build();
    }

    /* 7. 면접장소 전체 조회 */
    @GetMapping("/place")
    public ResponseEntity<List<PlaceResponse>> getAllPlace() {
        final List<PlaceResponse> placeResponses = recruitmentService.getAllPlace();
        return ResponseEntity.ok(placeResponses);
    }

    /* 8. 면접일정 등록 */
    @PostMapping("/interview-schedule/regist")
    public ResponseEntity<Void> registInterviewSchedule(
            @RequestBody InterviewScheduleCreateRequest request
    ) {
        try {
            System.out.println("Received request: " + request);
            recruitmentService.registInterviewSchedule(request);
            return ResponseEntity.created(URI.create("/recruitment/interview-schedule")).build();
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 9. 면접일정 상세 조회 */
    @GetMapping("interview-schedule/detail/{id}")
    public ResponseEntity<InterviewScheduleResponse> detailInterviewSchedule(@PathVariable Integer id) {
        InterviewScheduleResponse interviewScheduleResponse = recruitmentService.getInterviewScheduleById(id);
        return ResponseEntity.ok(interviewScheduleResponse);
    }

    /* 10. 면접일정 수정 */
    @PutMapping("interview-schedule/modify/{id}")
    public ResponseEntity<Void> modifyInterviewSchedule(
            @PathVariable Integer id,
            @RequestBody InterviewScheduleModifyRequest request
    ) {
        recruitmentService.modifyInterviewScheduleById(id, request);
        return ResponseEntity.created(URI.create("recruitment/interview-schedule/" + id)).build();
    }

    /* 11. 면접일정 삭제 */
    @DeleteMapping("interview-schedule/delete/{id}")
    public ResponseEntity<Void> deleteInterviewSchedule(@PathVariable Integer id) {
        recruitmentService.deleteInterviewSchedule(id);
        return ResponseEntity.noContent().build();
    }

    /* 12. 면접일정 전체 조회 */
    @GetMapping("/interview-schedule")
    public ResponseEntity<List<InterviewScheduleResponse>> getInterviewSchedule() {
        List<InterviewScheduleResponse> interviewScheduleResponse = recruitmentService.getInterviewSchedule();
        return ResponseEntity.ok(interviewScheduleResponse);
    }
}
