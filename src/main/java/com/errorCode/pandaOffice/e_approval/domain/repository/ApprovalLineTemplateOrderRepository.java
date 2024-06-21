package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLineTemplateOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalLineTemplateOrderRepository extends JpaRepository<ApprovalLineTemplateOrder, Integer> {
}
