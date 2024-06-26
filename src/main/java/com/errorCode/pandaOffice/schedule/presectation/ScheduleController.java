package com.errorCode.pandaOffice.schedule.presectation;

import com.errorCode.pandaOffice.schedule.dto.request.ScheduleCreateRequest;
import com.errorCode.pandaOffice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /* 1. 캘린더 일정 등록 */
    @PostMapping("/regist")
    public ResponseEntity<Void> registSchedule(
            @RequestBody ScheduleCreateRequest schedule
    ) {
        Integer id = scheduleService.registSchedule(schedule);
        return ResponseEntity.created(URI.create("/schedule/regist" + id)).build();
    }
}
