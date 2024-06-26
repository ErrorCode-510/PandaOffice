package com.errorCode.pandaOffice.schedule.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

    /* 1. 캘린더 일정 등록 */
    @Transactional
    public Integer registSchedule(ScheduleCreateRequest scheduleRes) {
        Schedule newSchedule = Schedule.of(
                scheduleRes.getName(),
                scheduleRes.getDescription(),
                scheduleRes.getStartDate(),
                scheduleRes.getEndDate(),
                scheduleRes.getStartTime(),
                scheduleRes.getEmployee()
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

    /* 3. 캘린더 일정 수정 */
    public void modifySchedule(Integer id, ScheduleCreateRequest scheduleRes) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정 엔티티가 비어있습니다."));
        Employee employee = employeeRepository.findById(scheduleRes.getEmployee().getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("사원 엔티티가 비어있습니다."));

        schedule.modify(
                scheduleRes.getName(),
                scheduleRes.getDescription(),
                scheduleRes.getStartDate(),
                scheduleRes.getEndDate(),
                scheduleRes.getStartTime(),
                employee
        );
    }

    /* 4. 캘린더 일정 삭제 */
    @Transactional
    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }
}
