package com.errorCode.pandaOffice.schedule.presectation;

import com.errorCode.pandaOffice.schedule.dto.request.ScheduleCreateRequest;
import com.errorCode.pandaOffice.schedule.dto.response.ScheduleResponse;
import com.errorCode.pandaOffice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /* 2. 캘린더 일정 조회 */
    @GetMapping("/detail/{id}")
    public ResponseEntity<ScheduleResponse> detailSchedule(
            @PathVariable Integer id
    ) {
        ScheduleResponse scheduleResponse = scheduleService.detailSchedule(id);
        return ResponseEntity.ok(scheduleResponse);
    }
}
