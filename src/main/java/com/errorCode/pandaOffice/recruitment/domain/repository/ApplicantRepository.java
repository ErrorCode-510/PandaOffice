package com.errorCode.pandaOffice.recruitment.domain.repository;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    /* 1. 면접자 성별 + 이름 검색 */
    Page<Applicant> findByGenderAndNameContaining(Pageable pageable, String gender, String name);

    /* 2. 면접자 주소 + 이름 검색 */
    Page<Applicant> findByAddressAndNameContaining(Pageable pageable, String address, String name);

    /* 3. 면접자 이름 검색 */
    Page<Applicant> findByNameContaining(Pageable pageable, String name);
}
