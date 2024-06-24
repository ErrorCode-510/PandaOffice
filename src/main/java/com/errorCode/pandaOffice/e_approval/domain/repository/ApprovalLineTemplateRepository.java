package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLineTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalLineTemplateRepository extends JpaRepository<ApprovalLineTemplate, Integer> {
}
