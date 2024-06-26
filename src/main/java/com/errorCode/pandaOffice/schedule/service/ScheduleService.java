package com.errorCode.pandaOffice.schedule.service;

import com.errorCode.pandaOffice.schedule.domain.entity.Schedule;
import com.errorCode.pandaOffice.schedule.domain.repository.ScheduleRepository;
import com.errorCode.pandaOffice.schedule.dto.request.ScheduleCreateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
