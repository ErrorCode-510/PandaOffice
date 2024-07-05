package com.errorCode.pandaOffice.recruitment.domain.repository;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import com.errorCode.pandaOffice.recruitment.dto.response.ApplicantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    /* 1. 면접자 성별 + 이름 조회 */
    Page<Applicant> findByGenderAndNameContaining(Pageable pageable, String gender, String name);

    /* 2. 면접자 성별 조회 */
    Page<Applicant> findByGender(Pageable pageable, String gender);

    /* 3. 면접자 주소 + 이름 조회 */
    Page<Applicant> findByAddressAndNameContaining(Pageable pageable, String address, String name);

    /* 4. 면접자 주소 조회 */
    Page<Applicant> findByAddress(Pageable pageable, String address);

    /* 5. 면접자 이름 조회 */
    Page<Applicant> findByNameContaining(Pageable pageable, String name);

//    /* 6. 면접자 나이 + 이름 조회 */
//    Page<Applicant> findByBirthDateBetweenAndContaining(Pageable pageable, String name, LocalDate startDate, LocalDate endDate);
//
//    /* 7. 나이 조회*/
//    Page<Applicant> findByBirthDateBetween(Pageable pageable, LocalDate startDate, LocalDate endDate);

}
