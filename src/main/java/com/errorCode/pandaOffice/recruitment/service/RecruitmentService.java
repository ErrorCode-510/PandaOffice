package com.errorCode.pandaOffice.recruitment.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.domain.entity.InterviewSchedule;
import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import com.errorCode.pandaOffice.recruitment.domain.repository.ApplicantRepository;
import com.errorCode.pandaOffice.recruitment.domain.repository.InterviewScheduleRepository;
import com.errorCode.pandaOffice.recruitment.domain.repository.PlaceRepository;
import com.errorCode.pandaOffice.recruitment.dto.request.ApplicantRequest;
import com.errorCode.pandaOffice.recruitment.dto.request.InterviewScheduleCreateRequest;
import com.errorCode.pandaOffice.recruitment.dto.request.InterviewScheduleModifyRequest;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.InterviewScheduleResponse;
import com.errorCode.pandaOffice.recruitment.dto.response.PlaceResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentService {

    private final ApplicantRepository applicantRepository;
    private final PlaceRepository placeRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final EmployeeRepository employeeRepository;

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

//        LocalDate now = LocalDate.now();
//        LocalDate startDate = null;
//        LocalDate endDate = null;

//        if (age != null) {
//            if (age >= 20 && age < 30) {
//                startDate = now.minusYears(30).plusDays(1);
//                endDate = now.minusYears(20);
//            } else if (age >= 30 && age < 40) {
//                startDate = now.minusYears(40).plusDays(1);
//                endDate = now.minusYears(30);
//            } else if (age >= 40 && age < 50) {
//                startDate = now.minusYears(50).plusDays(1);
//                endDate = now.minusYears(40);
//            }
//        }

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
//        /* 나이 + 이름 조회 */
//        else if (age != null && !name.isEmpty()) {
//            applicants = applicantRepository.findByBirthDateBetweenAndContaining(getPageable(page),name, startDate, endDate);
//        }
//        /* 나이 조회 */
//        else if (age != null) {
//            applicants = applicantRepository.findByBirthDateBetween(getPageable(page), startDate, endDate);
//        }
        /* 면접자 전체 조회 */
        else {
            applicants = applicantRepository.findAll(getPageable(page));
        }
        /* 반환 된 값은 현재 엔티티이며, from(Mapper 대신 사용)을 사용해 DTO 타입으로 변경 */
        return applicants.map(ApplicantResponse::from);
    }

    /* 3. 면접자 등록 */
    @Transactional
    public Integer registApplicant(ApplicantRequest applicantRequest) {

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
    public void modify(Integer id, ApplicantRequest applicantRequest) {
        Optional<Applicant>applicantOptional = applicantRepository.findById(id);
        if (applicantOptional.isPresent()) {
            Applicant applicant = applicantOptional.get();
            applicant.modify(
                    applicantRequest.getName(),
                    applicantRequest.getBirthDate(),
                    applicantRequest.getGender(),
                    applicantRequest.getPhone(),
                    applicantRequest.getAddress(),
                    applicantRequest.getEmail()
            );
        }
    }

    /* 6. 면접자 삭제 */
    @Transactional
    public void deleteApplicant(Integer id) {
        applicantRepository.deleteById((id));
    }

    /* 7. 면접장소 전체 조회 */
    @Transactional(readOnly = true)
    public List<PlaceResponse> getAllPlace() {
        List<Place> places = placeRepository.findAll();

        for (Place place : places) {
            System.out.println("place = " + place);
        }

        List<PlaceResponse> placeResponses = places.stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());

        return placeResponses;
    }

    /* 8. 면접일정 등록 */
    @Transactional
    public Integer registInterviewSchedule(InterviewScheduleCreateRequest request) {

        /* id로 면접장소 찾기 및 없을 시 Exception 처리 */
        Place place = placeRepository.findById(request.getPlace().getId())
                .orElseThrow(() -> new EntityNotFoundException("장소 엔티티가 비어있습니다."));
        /* id로 사원정보 찾기 및 없을 시 Exception 처리 */
        Employee employee = employeeRepository.findById(request.getEmployee().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원 엔티티가 비어있습니다."));
        Employee employee2 = employeeRepository.findById(request.getEmployee2().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원2 엔티티가 비어있습니다."));
        Employee employee3 = employeeRepository.findById(request.getEmployee3().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원3 엔티티가 비어있습니다."));
        /* id로 면접자 찾기 및 없을 시 Exception 처리 */
        List<Applicant> applicants = new ArrayList<>();
        for (Applicant applicantId : request.getApplicantList()) {
            Applicant applicant = applicantRepository.findById(applicantId.getId())
                    .orElseThrow( () -> new EntityNotFoundException("면접자 엔티티가 비어있습니다."));
            applicants.add(applicant);
        }
        InterviewSchedule newInterviewSchedule = InterviewSchedule.of(
                request.getName(),
                request.getMemo(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStartTime(),
                place,
                employee,
                employee2,
                employee3,
                applicants
        );
        final InterviewSchedule interviewSchedule = interviewScheduleRepository.save(newInterviewSchedule);
        return interviewSchedule.getId();
    }

    /* 9. 면접일정 상세 조회 */
    @Transactional(readOnly = true)
    public InterviewScheduleResponse getInterviewScheduleById(Integer id) {

        InterviewSchedule interviewSchedule = interviewScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("조회하신 면접일정이 비어있습니다."));

        return InterviewScheduleResponse.from(interviewSchedule);
    }

    /* 10. 면접일정 수정 */
    @Transactional
    public void modifyInterviewScheduleById(Integer id, InterviewScheduleModifyRequest request) {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("면접일정이 비어있습니다."));

        /* DB에서 세션이 겹치지 않기 위해 사원 조회 */
        Employee employee = employeeRepository.findById(request.getEmployee().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원 엔티티가 비어있습니다."));
        Employee employee2 = employeeRepository.findById(request.getEmployee2().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원2 엔티티가 비어있습니다."));
        Employee employee3 = employeeRepository.findById(request.getEmployee3().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원3 엔티티가 비어있습니다."));

        /* DB에서 세션이 겹치지 않기 위해 면접자 조회 */
        List<Applicant> applicants = request.getApplicantList().stream().map(
                applicant -> applicantRepository.findById(applicant.getId())
                        .orElseThrow(() -> new EntityNotFoundException("면접자 엔티티가 비어있습니다."))
        ).collect(Collectors.toList());

        interviewSchedule.modify(
                request.getName(),
                request.getMemo(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStartTime(),
                request.getPlace(),
                employee,
                employee2,
                employee3,
                applicants
        );
    }

    /* 11. 면접일정 삭제 */
    @Transactional
    public void deleteInterviewSchedule(Integer id) {
        interviewScheduleRepository.deleteById(id);
    }
}
