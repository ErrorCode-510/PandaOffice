package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.*;
import com.errorCode.pandaOffice.e_approval.domain.repository.specification.ApprovalDocumentSpecification;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentService {
    private final ApprovalDocumentRepository approvalDocumentRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final ApprovalLineTemplateFolderRepository approvalLineTemplateFolderRepository;
    private final ApprovalLineTemplateOrderRepository approvalLineTemplateOrderRepository;
    private final ApprovalLineTemplateRepository approvalLineTemplateRepository;
    private final AutoApprovalLineRepository autoApprovalLineRepository;
    private final DepartmentBoxRepository departmentBoxRepository;
    private final DepartmentDocumentRepository departmentDocumentRepository;
    private final DocumentAttachedFileRepository documentAttachedFileRepository;
    private final DocumentTemplateRepository documentTemplateRepository;
    private final DocumentTemplateFolderRepository doTemplateFolderRepository;
    private final EmployeeRepository employeeRepository;


    public Page<ApprovalDocumentListResponse> searchDocuments(LocalDate startDate, LocalDate endDate, Integer templateId, String title, String draftEmployeeName, Integer status, Integer nowPage, Integer pageSize) {

        /* pageable 객체 */
        int pageNumber = (nowPage != null) ? nowPage : 0;
        int pageSizeValue = (pageSize != null) ? pageSize : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSizeValue);

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
            spec = spec.and(ApprovalDocumentSpecification.eqStatus(status));
        }
        /* Page 객체 반환 */
        Page<ApprovalDocument> documents = approvalDocumentRepository.findAll(spec, pageable);
        /* from 을 사용하여 response 로 변환 */
        return documents.map(ApprovalDocumentListResponse::from);
    }

    public ApprovalDocumentDetailResponse getDocumentDetail(int documentId) {
        /* 익셉션 할당 필요 */
        ApprovalDocument document = approvalDocumentRepository.findById(documentId).orElseThrow();
        return ApprovalDocumentDetailResponse.of(document);
    }

    public Long createApprovalDocument(CreateApprovalDocumentRequest documentRequest) {
        final DocumentTemplate documentTemplate = documentTemplateRepository.findById(documentRequest.getDocumentTemplateId())
                .orElseThrow();
        final Employee draftEmployee = employeeRepository.findById(documentRequest.getEmployeeId())
                .orElseThrow();
        return null;
    }
}
