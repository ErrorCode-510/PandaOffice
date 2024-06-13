package com.errorCode.pandaOffice.recruitment.domain.repository;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
}
