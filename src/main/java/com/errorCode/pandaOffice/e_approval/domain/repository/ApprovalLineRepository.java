package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Integer> {
}
