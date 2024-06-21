package com.errorCode.pandaOffice.e_approval.domain.repository.specification;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface ApprovalDocumentSpecification {
    /* 검색 조건 쿼리 자동 생성 */
    static Specification<ApprovalDocument> gteStartDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("approvalDate"), startDate);
    }
    static Specification<ApprovalDocument> lteEndDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("lastApprovalDate"), endDate);
    }
    static Specification<ApprovalDocument> eqTemplateId(Integer templateId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("templateId"), templateId);
    }
    static Specification<ApprovalDocument> containsTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
    static Specification<ApprovalDocument> containsName(String draftEmployeeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("draftEmployee").get("name"), "%" + draftEmployeeName + "%");
    }
    static Specification<ApprovalDocument> eqStatus(Integer status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
