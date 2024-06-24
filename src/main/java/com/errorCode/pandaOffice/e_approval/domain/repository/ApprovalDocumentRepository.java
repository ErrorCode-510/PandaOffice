package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalDocumentRepository extends JpaRepository<ApprovalDocument, Integer>, JpaSpecificationExecutor<ApprovalDocument> {

}
