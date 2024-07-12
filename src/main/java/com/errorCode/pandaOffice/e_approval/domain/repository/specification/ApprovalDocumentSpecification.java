package com.errorCode.pandaOffice.e_approval.domain.repository.specification;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLine;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface ApprovalDocumentSpecification {
    static Specification<ApprovalDocument> gteStartDate(LocalDate startDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("draftDate"), startDate);
    }

    static Specification<ApprovalDocument> lteEndDate(LocalDate endDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("draftDate"), endDate);
    }

    static Specification<ApprovalDocument> likeTemplateName(String templateName) {
        return (root, query, cb) -> {
            Join<ApprovalDocument, DocumentTemplate> join = root.join("documentTemplate", JoinType.LEFT);
            return cb.like(join.get("name"), "%" + templateName + "%");
        };
    }

    static Specification<ApprovalDocument> likeDocumentName(String name) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + name + "%");
    }

    static Specification<ApprovalDocument> likeEmployeeName(String draftEmployeeName) {
        return (root, query, criteriaBuilder) -> {
            Join<ApprovalDocument, Employee> join = root.join("draftEmployee", JoinType.LEFT);
            return criteriaBuilder.like(join.get("name"), "%" + draftEmployeeName + "%");
        };
    }

    static Specification<ApprovalDocument> eqStatus(ApprovalStatus approvalStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), approvalStatus);
    }

    static Specification<ApprovalDocument> eqApprovalType(ApproveType approveType, int employeeId) {
        return (root, query, criteriaBuilder) -> {
            Join<ApprovalDocument, ApprovalLine> approvalLineJoin = root.join("approvalLineList", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(approvalLineJoin.get("employee").get("employeeId"), employeeId),
                    criteriaBuilder.equal(approvalLineJoin.get("status"), approveType)
            );
        };
    }
}
