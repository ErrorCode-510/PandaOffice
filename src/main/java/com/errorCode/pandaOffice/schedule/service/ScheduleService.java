package com.errorCode.pandaOffice.schedule.service;

import com.errorCode.pandaOffice.schedule.domain.entity.Schedule;
import com.errorCode.pandaOffice.schedule.domain.repository.ScheduleRepository;
import com.errorCode.pandaOffice.schedule.dto.request.ScheduleCreateRequest;
import com.errorCode.pandaOffice.schedule.dto.response.ScheduleResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /* 1. 캘린더 일정 등록 */
    @Transactional
    public Integer registSchedule(ScheduleCreateRequest schedule) {
        Schedule newSchedule = Schedule.of(
                schedule.getName(),
                schedule.getDescription(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getStartTime(),
                schedule.getEmployee()
        );
        return scheduleRepository.save(newSchedule).getId();
    }

    /* 2. 캘린더 일정 조회 */
    @Transactional(readOnly = true)
    public ScheduleResponse detailSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("일정 엔티티가 비어있습니다."));
        return ScheduleResponse.from(schedule);
    }
}
