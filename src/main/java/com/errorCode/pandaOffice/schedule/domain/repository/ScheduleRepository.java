package com.errorCode.pandaOffice.schedule.domain.repository;

import com.errorCode.pandaOffice.schedule.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
