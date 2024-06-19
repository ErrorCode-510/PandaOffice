package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.repository.ApprovalDocumentRepository;
import com.errorCode.pandaOffice.e_approval.domain.repository.ApprovalDocumentSpecification;
import com.errorCode.pandaOffice.e_approval.dto.response.ApproveDocumentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentService {
    private final ApprovalDocumentRepository approvalDocumentRepository;


    public Page<ApproveDocumentListResponse> searchDocuments(LocalDate startDate, LocalDate endDate, Integer templateId, String title, String draftEmployeeName, String status, Integer pageSize) {

        /* pageable 객체 */
        Pageable pageable = PageRequest.of(0, pageSize);

        /**
         * 각각의 조건들을 확인하고 동적으로 쿼리메소드를 만든다.
         * @version : 1
         * @author : 편승준
         * @see : ApprovalDocumentSpecification - 쿼리메소드 작성하는 클래스
         * @return : find 의 명세서 spec 객체
         * */
        Specification<ApprovalDocument> spec = ((root, query, criteriaBuilder) -> null);
        if (startDate != null)
            spec = spec.and(ApprovalDocumentSpecification.gteStartDate(startDate));
        if(endDate != null)
            spec = spec.and(ApprovalDocumentSpecification.lteEndDate(endDate));
        if(templateId != null)
            spec = spec.and(ApprovalDocumentSpecification.eqTemplateId(templateId));
        if(title != null)
            spec = spec.and(ApprovalDocumentSpecification.containsTitle(title));
        if(draftEmployeeName != null)
            spec = spec.and(ApprovalDocumentSpecification.containsName(draftEmployeeName));
        if(status != null){
            spec = spec.and(ApprovalDocumentSpecification.isStatus(status));
        }
        /* Page 객체 반환 */
        Page<ApprovalDocument> documents = approvalDocumentRepository.findAll(spec, pageable);
        /* from 을 사용하여 response 로 변환 */
        return documents.map(ApproveDocumentListResponse::from);
    }
}
