package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.common.util.FileUploadUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLine;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentAttachedFile;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.*;
import com.errorCode.pandaOffice.e_approval.domain.repository.specification.ApprovalDocumentSpecification;
import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveTypeCalculator;
import com.errorCode.pandaOffice.e_approval.dto.EmployeeResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.response.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.response.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.request.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.request.UpdateApprovalDocumentRequest;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentService {
    private final ApprovalDocumentRepository approvalDocumentRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final AutoApprovalLineRepository autoApprovalLineRepository;
    private final DepartmentBoxRepository departmentBoxRepository;
    private final DepartmentDocumentRepository departmentDocumentRepository;
    private final DocumentAttachedFileRepository documentAttachedFileRepository;
    private final DocumentTemplateRepository documentTemplateRepository;
    private final DocumentTemplateFolderRepository doTemplateFolderRepository;
    private final EmployeeRepository employeeRepository;
    private final ApproveTypeCalculator calculator;
    private final ApproveService approveService;

    @Value("${approvalDocumentAttachedFile.file-url}")
    private String FILE_URL;
    @Value("${approvalDocumentAttachedFile.file-dir}")
    private String FILE_DIR;


    /**
     * 여러가지 검색 필터를 통하여 검색하는 메소드
     * <p>
     * 검색 필터를 확인하여 Specification 객체를 셋팅하고 find 한다.
     * </p>
     *
     * @param templateId        템플릿 ID
     * @param title             서류 제목
     * @param pageSize          페이지당 항목 수
     * @param startDate         검색 시작 날짜 (포함)
     * @param endDate           검색 종료 날짜 (포함)
     * @param draftEmployeeName 작성자 이름
     * @param status            서류 상태 (0 = 승인, 1 = 진행중, 2 = 반려)
     * @param approvalStatus
     * @param nowPage           현재 페이지 번호 (0부터 시작)
     * @return spec 객체와 pageable 객체를 사용하여 find 한 문서 Page 객체
     * @version 1
     * @author 편승준
     */
    public Page<ApprovalDocumentListResponse> searchDocuments(LocalDate startDate,
                                                              LocalDate endDate,
                                                              String templateName,
                                                              String name,
                                                              String draftEmployeeName,
                                                              Integer  status,
                                                              Integer approvalStatus,
                                                              Integer nowPage,
                                                              /* 자신의 문서 */
                                                              boolean mine,
                                                              boolean handling
                                                              ) {
        /* pageable 객체 */
        int employeeId = TokenUtils.getEmployeeId();
        int pageNumber = (nowPage != null) ? nowPage - 1 : 0;
        int pageSizeValue = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSizeValue);
        Specification<ApprovalDocument> spec = ((root, query, criteriaBuilder) -> null);
        if (startDate != null)
            spec = spec.and(ApprovalDocumentSpecification.gteStartDate(startDate));
        if(endDate != null)
            spec = spec.and(ApprovalDocumentSpecification.lteEndDate(endDate));
        if(templateName != null)
            spec = spec.and(ApprovalDocumentSpecification.likeTemplateName(templateName));
        if(name != null)
            spec = spec.and(ApprovalDocumentSpecification.likeDocumentName(name));
        if(draftEmployeeName != null)
            spec = spec.and(ApprovalDocumentSpecification.likeEmployeeName(draftEmployeeName));
        if(status != null)
            spec = spec.and(ApprovalDocumentSpecification.eqStatus(ApprovalStatus.fromValue(status)));
        if(approvalStatus != null)
            spec = spec.and(ApprovalDocumentSpecification.eqApprovalType(ApproveType.fromValue(approvalStatus), employeeId));


        final Page<ApprovalDocument> documents = approvalDocumentRepository.findAll(spec, pageable);
        /* from 을 사용하여 response 로 변환 */
        return documents.map(ApprovalDocumentListResponse::from);
    }


    /**
     * 상세보기에 필요한 정보들을 가져오는 메소드
     *
     * @param documentId 문서의 ID
     * @return 문서 response 객체
     */
//    public  getDocumentDetail(int documentId) {
//
//        final ApprovalDocument document = approvalDocumentRepository.findById(documentId).orElseThrow();
//    }
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
        /* 토큰 정보를 조회하여 사원 정보를 가져온다. */
        final int employeeId = TokenUtils.getEmployeeId();
        Employee draftEmployee = employeeRepository.findById(employeeId)
                .orElseThrow();
        /* REQUEST 안의 결재선 정보를 가져와 엔티티 리스트를 작성한다. */
        final List<ApprovalLine> approvalLine = documentRequest.getApprovalLineList().stream()
                .map(line -> ApprovalLine.of(line.getOrder(),
                        employeeRepository.findById(line.getEmployeeId())
                                .orElseThrow()
                ))
                .toList();
        /* 요청 정보, 템플릿 엔티티, 사원 엔티티, 결재선 라인 엔티티, 파일 저장 엔티티를 인자로 받아 결재 서류를 작성한다. */
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
        final ApproveType requestType = ApproveType.fromValue(documentRequest.getType());

        switch (requestType) {
            case APPROVE:
                approveService.approve(documentRequest);
                break;
            case DENY:
                approveService.deny(documentRequest);
                break;
            case PRE_APPROVE:
                approveService.preApprove(documentRequest);
                break;
            case ALL_APPROVE:
                approveService.allApprove(documentRequest);
                break;
        }
    }

    public void deleteApprovalDocument(int documentId) {
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        ApprovalDocument document = approvalDocumentRepository.findById(documentId).orElse(null);
//        if (employee != document.getDraftEmployee()) {
//            throw new RuntimeException("기안자 정보가 일치하지 않습니다.");
//        }

        try {
            if (document == null) {
                throw new Exception();
            }
            if (document.getApprovalLineList().get(0).getStatus() != ApproveType.PENDING) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        approvalDocumentRepository.deleteById(documentId);
    }

    public ApprovalDocumentDetailResponse findByDocumentId(int documentId) {
        ApprovalDocument entity = approvalDocumentRepository.findById(documentId)
                .orElseThrow();
        Employee currentEmployee = employeeRepository.findById(TokenUtils.getEmployeeId())
                .orElseThrow();
        return ApprovalDocumentDetailResponse.builder()
                .id(entity.getId())
                .currentEmployee(EmployeeResponse.of(entity.getDraftEmployee()))
                .draftEmployee(EmployeeResponse.of(currentEmployee))
                .name(entity.getTitle())
                .templateName(entity.getDocumentTemplate().getName())
                .title(entity.getDocumentTemplate().getTitle())
                .status(entity.getStatus())
                .document(entity.getDocument())
                .draftDate(entity.getDraftDate())
                .approvalLineList(entity.getApprovalLineList()
                        .stream()
                        .map(lineEntity -> ApprovalDocumentDetailResponse.ApprovalLineResponse.builder()
                                .order(lineEntity.getOrder())
                                .employee(EmployeeResponse.of(lineEntity.getEmployee()))
                                .comment(lineEntity.getComment())
                                .handlingDate(lineEntity.getHandlingDate())
                                .status(calculator.convertResult(lineEntity.getStatus()))
                                .approvalAbleList(calculator.calcAble(lineEntity.getStatus()
                                        , lineEntity.getEmployee().getJob()))
                                .build())
                        .toList()
                )
                .build();
    }


}
