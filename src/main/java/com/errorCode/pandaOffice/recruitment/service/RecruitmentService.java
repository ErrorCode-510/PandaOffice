package com.errorCode.pandaOffice.recruitment.service;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.domain.entity.InterviewSchedule;
import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import com.errorCode.pandaOffice.recruitment.domain.repository.ApplicantRepository;
import com.errorCode.pandaOffice.recruitment.domain.repository.InterviewScheduleRepository;
import com.errorCode.pandaOffice.recruitment.domain.repository.PlaceRepository;
import com.errorCode.pandaOffice.recruitment.dto.request.ApplicantCreateRequest;
import com.errorCode.pandaOffice.recruitment.dto.request.InterviewScheduleCreateRequest;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.InterviewScheduleResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.PlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentService {

    private final ApplicantRepository applicantRepository;
    private final PlaceRepository placeRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    /* 현재 페이지와 한 페이지당 보여줄 페이지 개수 */
    private Pageable getPageable(final Integer page) {
        /* 페이지 번호는 0부터 시작 */
        return PageRequest.of(page -1, 10);
    }

    /* 1. 면접자 전체 조회 */
    @Transactional(readOnly = true)
    public Page<ApplicantResponse> getAllApplicant(Integer page) {
        Page<Applicant> applicants = applicantRepository.findAll(getPageable(page));
        return applicants.map(ApplicantResponse::from);
    }

    /* 2. 면접자 검색 조회 */
    @Transactional(readOnly = true)
    /* Containing: 해당 값이 포함되어 있는 양옆 모든 값 */
    public Page<ApplicantResponse> getSearchApplicant(Integer page, String gender, String address, String name) {

        Page<Applicant> applicants = null;

        /* 성별 + 이름 조회 */
        if (gender != null && !gender.isEmpty() && name != null && !name.isEmpty()) {
            applicants = applicantRepository.findByGenderAndNameContaining(getPageable(page), gender, name);
        }
        /* 성별 조회 */
        else if (gender != null && !gender.isEmpty()) {
            applicants = applicantRepository.findByGender(getPageable(page), gender);
        }
        /* 주소 + 이름 조회 */
        else if (address != null && !address.isEmpty() && name != null && !name.isEmpty()) {
            applicants = applicantRepository.findByAddressAndNameContaining(getPageable(page), address, name);
        }
        /* 주소 */
        else if (address != null && !address.isEmpty()) {
            applicants = applicantRepository.findByAddress(getPageable(page), address);
        }
        /* 이름 조회 */
        else if (name != null && !name.isEmpty()) {
            applicants = applicantRepository.findByNameContaining(getPageable(page), name);
        }
        /* 면접자 전체 조회 */
        else {
            applicants = applicantRepository.findAll(getPageable(page));
        }
        /* 반환 된 값은 현재 엔티티이며, from(Mapper 대신 사용)을 사용해 DTO 타입으로 변경 */
        return applicants.map(ApplicantResponse::from);
    }

    /* 3. 면접자 등록 */
    public Integer registApplicant(ApplicantCreateRequest applicantRequest) {

        final Applicant newApplicant = Applicant.of(
                applicantRequest.getName(),
                applicantRequest.getBirthDate(),
                applicantRequest.getGender(),
                applicantRequest.getPhone(),
                applicantRequest.getAddress(),
                applicantRequest.getEmail()
        );

        Applicant applicant = applicantRepository.save(newApplicant);

        return applicant.getId();
    }

    /* 4. 면접자 상세 조회 */
    @Transactional(readOnly = true)
    public ApplicantResponse getApplicantById(Integer id) {
        Optional<Applicant> applicantOptional = applicantRepository.findById(id);

        /* isPresent: Optional에서 제공하는 메소드, 객체가 비어있지 않으면 true 반환
        * if: 객체가 비어있다면 true 반환 */
        if (!applicantOptional.isPresent()) {
            return null;
        }
        Applicant applicant = applicantOptional.get();
        return ApplicantResponse.from(applicant);
    }

    /* 5. 면접자 정보 수정 */
    @Transactional
    public void modify(Integer id, ApplicantCreateRequest applicantCreateRequest) {
        Optional<Applicant>applicantOptional = applicantRepository.findById(id);
        if (applicantOptional.isPresent()) {
            Applicant applicant = applicantOptional.get();
            applicant.modify(
                    applicantCreateRequest.getName(),
                    applicantCreateRequest.getBirthDate(),
                    applicantCreateRequest.getGender(),
                    applicantCreateRequest.getPhone(),
                    applicantCreateRequest.getAddress(),
                    applicantCreateRequest.getEmail()
            );
        }
    }

    /* 6. 면접자 삭제 */
    public void remove(Integer id) {
        applicantRepository.deleteById((id));
    }

    /* 7. 면접장소 전체 조회 */
//    @Transactional(readOnly = true)
//    public List<PlaceResponse> getAllPlace() {
//        List<Place> places = placeRepository.findAll();
//
//        List<PlaceResponse> placeResponses = places.stream()
//                .map(PlaceResponse::from)
//                .collect(Collectors.toList());
//
//        return placeResponses;
//    }

    /* 8. 면접일정 상세 조회
    * 프론트에서 면접관은 몇명인지 보여주는 기능 필요 */
//    @Transactional(readOnly = true)
//    public InterviewScheduleResponse getInterviewScheduleById(Integer id) {
//        Optional<InterviewSchedule> interviewScheduleOptional = interviewScheduleRepository.findById(id);
//        if (!interviewScheduleOptional.isPresent()) {
//            return null;
//        }
//        InterviewSchedule interviewSchedule = interviewScheduleOptional.get();
//        return InterviewScheduleResponse.from(interviewSchedule);
//    }

    /* 9. 면접일정 등록 */
//    public Integer registInterviewSchedule(InterviewScheduleCreateRequest interviewScheduleRequest) {
//        final InterviewSchedule newInterviewSchedule = InterviewSchedule.of(
//                interviewScheduleRequest.getName(),
//                interviewScheduleRequest.getMemo(),
//                interviewScheduleRequest.getStartDate(),
//                interviewScheduleRequest.getEndDate(),
//                interviewScheduleRequest.getStartTime(),
//                interviewScheduleRequest.getPlace(),
//                interviewScheduleRequest.getEmployee(),
//                interviewScheduleRequest.getEmployee2(),
//                interviewScheduleRequest.getEmployee3(),
//                interviewScheduleRequest.getApplicantIdList()
//        );
//        final InterviewSchedule interviewSchedule = interviewScheduleRepository.save(newInterviewSchedule);
//        return interviewSchedule.getId();
//    }
}
