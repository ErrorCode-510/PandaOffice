package com.errorCode.pandaOffice.recruitment.domain.repository;

import com.errorCode.pandaOffice.recruitment.domain.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    /* 1. 면접자 성별 && 이름 검색 */
    /* ?1: 첫 번째 파라미터를 받겠다는 뜻, gender
    * %?2%: 두 번째 파라미터를 받겠다는 뜻, name: 앞 뒤로 뭐가 오던지 다 검색하겠다. */
    @Query("SELECT a FROM Applicant a WHERE a.gender = ?1 AND a.name LIKE %?2%")
    Page<Applicant> findByApplicantGender(Pageable pageable, String gender, String name);

}
