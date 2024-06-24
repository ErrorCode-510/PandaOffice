package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.common.util.FileUploadUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLine;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentAttachedFile;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.*;
import com.errorCode.pandaOffice.e_approval.domain.repository.specification.ApprovalDocumentSpecification;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.UpdateApprovalDocumentRequest;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

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

    @Value("${approvalDocumentAttachedFile.file-url}")
    private String FILE_URL;
    @Value("${approvalDocumentAttachedFile.file-dir}")
    private String FILE_DIR;


    /**
     * 여러가지 검색 필터를 통하여 검색하는 메소드
     * <p>
     *     검색 필터를 확인하여 Specification 객체를 셋팅하고 find 한다.
     * </p>
     * @version 1
     * @param startDate 검색 시작 날짜 (포함)
     * @param endDate 검색 종료 날짜 (포함)
     * @param templateId 템플릿 ID
     * @param title 서류 제목
     * @param draftEmployeeName 작성자 이름
     * @param status 서류 상태 (0 = 승인, 1 = 진행중, 2 = 반려)
     * @param nowPage 현재 페이지 번호 (0부터 시작)
     * @param pageSize 페이지당 항목 수
     * @return spec 객체와 pageable 객체를 사용하여 find 한 문서 Page 객체
     * @author 편승준
     * */
    public Page<ApprovalDocumentListResponse> searchDocuments(LocalDate startDate, LocalDate endDate, Integer templateId, String title, String draftEmployeeName, Integer status, Integer nowPage, Integer pageSize) {

        /* pageable 객체 */
        int pageNumber = (nowPage != null) ? nowPage - 1 : 0;
        int pageSizeValue = (pageSize != null) ? pageSize : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSizeValue);

        /* 조건에 따라 유동적으로 쿼리메소드 작성 */
        Specification<ApprovalDocument> spec = ((root, query, criteriaBuilder) -> null);
        if (startDate != null)
            spec = spec.and(ApprovalDocumentSpecification.gteStartDate(startDate));
        if (endDate != null)
            spec = spec.and(ApprovalDocumentSpecification.lteEndDate(endDate));
        if (templateId != null)
            spec = spec.and(ApprovalDocumentSpecification.eqTemplateId(templateId));
        if (title != null)
            spec = spec.and(ApprovalDocumentSpecification.containsTitle(title));
        if (draftEmployeeName != null)
            spec = spec.and(ApprovalDocumentSpecification.containsName(draftEmployeeName));
        if (status != null) {
            spec = spec.and(ApprovalDocumentSpecification.eqStatus(status));
        }
        /* Page 객체 반환 */
        final Page<ApprovalDocument> documents = approvalDocumentRepository.findAll(spec, pageable);
        /* from 을 사용하여 response 로 변환 */
        return documents.map(ApprovalDocumentListResponse::from);
    }

    /**
     * 상세보기에 필요한 정보들을 가져오는 메소드
     * @param documentId 문서의 ID
     * @return 문서 response 객체
     */
    public ApprovalDocumentDetailResponse getDocumentDetail(int documentId) {
        /* 익셉션 할당 필요 */
        final ApprovalDocument document = approvalDocumentRepository.findById(documentId).orElseThrow();
        return ApprovalDocumentDetailResponse.of(document);
    }

    public int createApprovalDocument(CreateApprovalDocumentRequest documentRequest, List<MultipartFile> attachedFile) {


        /* 파일 저장 로직. 파일을 저장하고 원본명, 경로, 타입을 가진 엔티티 리스트를 반환한다. */
        List<DocumentAttachedFile> documentAttachedFileList = new ArrayList<>();
        if (attachedFile != null) {
            attachedFile.forEach(file -> {
                String randomName = UUID.randomUUID().toString().replace("-", "");
                String path = FileUploadUtils.saveFile(FILE_DIR, randomName, file);
                documentAttachedFileList.add(DocumentAttachedFile.from(file.getName(), path, file.getContentType()));
            });
        }

        /* 서류 템플릿을 불러온다. */
        final DocumentTemplate documentTemplate = documentTemplateRepository.findById(documentRequest.getDocumentTemplateId())
                .orElseThrow();
        /* 요청에 있는 기안자 ID를 통하여 사원을 반환한다. 시큐리티 완성 시 수정이 필요한 메소드. */
        final Employee draftEmployee = employeeRepository.findById(1)   //현재는 1로 하드코딩
                .orElseThrow();
        /* REQUEST 안의 결재선 정보를 가져와 엔티티 리스트를 작성한다. */
        final List<ApprovalLine> approvalLine = documentRequest.getApprovalLineList()
                .stream().map(
                        request -> {
                            Employee employee = null;
                            if (request.getEmployeeId() != null) {
                                employee = employeeRepository.findById(request.getEmployeeId())
                                        .orElseThrow();
                            } else if (request.getDepartmentId() != null && request.getJobId() != null) {
                                employee = (Employee) employeeRepository.findFirstByDepartmentIdAndJobIdOrderByEmployeeIdAsc(request.getDepartmentId(), request.getJobId())
                                        .orElseThrow();
                            }
                            return ApprovalLine.of(request, employee);
                        }
                )
                .toList();
        /* 요청 정보, 템플릿 엔티티, 사원 엔티티, 결재선 라인 엔티티, 파일 저장 엔티티를 인자로 받아 결재 서류를 작성한다.
         * 영속성 전이를 이용하여 결재 서류만 작성해도 된다. */
        ApprovalDocument newDocument = ApprovalDocument.of(
                documentRequest,
                documentTemplate,
                draftEmployee,
                approvalLine,
                documentAttachedFileList);
        final ApprovalDocument approvalDocument = approvalDocumentRepository.save(newDocument);
        return approvalDocument.getId();
    }

    public void updateApprovalDocument(UpdateApprovalDocumentRequest documentRequest) {
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();

    }
}
