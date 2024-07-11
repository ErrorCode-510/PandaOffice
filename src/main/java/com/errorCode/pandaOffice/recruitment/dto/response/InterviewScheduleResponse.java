package com.errorCode.pandaOffice.recruitment.dto.response;

import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.domain.entity.InterviewSchedule;
import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewScheduleResponse {

    /* 면접 일정 코드 */
    private final int id;

    /* 일정명 */
    private final String name;

    /* 메모 */
    private final String memo;

    /* 일정 시작일 */
    private final LocalDate startDate;

    /* 일정 종료일 */
    private final LocalDate endDate;

    /* 일정 시작 일시 */
    private final LocalTime startTime;

    /* 면접 장소 */
    private final PlaceResponse place;

    /* 면접관들 */
    private final ScheduleEmployeeResponse employee;

//    /* 면접관들 2 */
//    private final ScheduleEmployeeResponse employee2;
//
//    /* 면접관들 3 */
//    private final ScheduleEmployeeResponse employee3;

    /* 면접자들 */
    private final List<ApplicantResponse> applicantList;

    /* 빌드를 사용한 엔티티 -> DTO 변환 */
    public static InterviewScheduleResponse from(final InterviewSchedule interviewSchedule) {
        return InterviewScheduleResponse.builder()
                .id(interviewSchedule.getId())
                .name(interviewSchedule.getName())
                .memo(interviewSchedule.getMemo())
                .startDate(interviewSchedule.getStartDate())
                .endDate(interviewSchedule.getEndDate())
                .startTime(interviewSchedule.getStartTime())
                .place(PlaceResponse.builder()
                        .id(interviewSchedule.getPlace().getId())
                        .name(interviewSchedule.getPlace().getName())
                        .build())
                .employee(ScheduleEmployeeResponse.builder()
                        .id(interviewSchedule.getEmployee().getEmployeeId())
                        .name(interviewSchedule.getEmployee().getName())
                        .build())
//                .employee2(ScheduleEmployeeResponse.builder()
//                        .id(interviewSchedule.getEmployee2().getEmployeeId())
//                        .name(interviewSchedule.getEmployee().getName())
//                        .build())
//                .employee3(ScheduleEmployeeResponse.builder()
//                        .id(interviewSchedule.getEmployee3().getEmployeeId())
//                        .name(interviewSchedule.getEmployee().getName())
//                        .build())
                .applicantList(interviewSchedule.getApplicantsList().stream()
                        .map(ApplicantResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
