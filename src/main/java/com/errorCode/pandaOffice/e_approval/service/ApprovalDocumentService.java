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
import org.springframework.security.core.parameters.P;
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
     * 검색 필터를 확인하여 Specification 객체를 셋팅하고 find 한다.
     * </p>
     *
     * @param startDate         검색 시작 날짜 (포함)
     * @param endDate           검색 종료 날짜 (포함)
     * @param templateId        템플릿 ID
     * @param title             서류 제목
     * @param draftEmployeeName 작성자 이름
     * @param status            서류 상태 (0 = 승인, 1 = 진행중, 2 = 반려)
     * @param nowPage           현재 페이지 번호 (0부터 시작)
     * @param pageSize          페이지당 항목 수
     * @return spec 객체와 pageable 객체를 사용하여 find 한 문서 Page 객체
     * @version 1
     * @author 편승준
     */
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
     *
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
        /* 토큰 정보를 조회하여 사원 정보를 가져온다. */
        final int employeeId = TokenUtils.getEmployeeId();
        Employee draftEmployee = employeeRepository.findById(employeeId)
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
        /* 현재 처리할 서류의 정보를 가져옫다. */
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();
        /* 처리할 서류의 현재 결재선 정보를 가져온다 */
        List<ApprovalLine> approvalLineList = document.getApprovalLineList();
        /* 현재 로그인된 사원의 정보 */
        final int employeeId = TokenUtils.getEmployeeId();
        final Employee currentEmployee = employeeRepository.findById(employeeId)
                .orElseThrow();
        /* request 의 결재 방식을 가져와서 switch 문으로 처리 방식을 다르게 한다. */
        switch (documentRequest.getType()) {
            /* 승인처리 과정 */
            case 2: {
                /* 현재 결재 라인 */
                ApprovalLine currentLine = approvalLineList.stream()
                        .filter(line -> line.getId() == documentRequest.getApprovalLineId())
                        .findFirst()
                        .orElse(null);
                /* 다음 결재 라인 */
                ApprovalLine nextLine = approvalLineList.stream()
                        .filter(line -> line.getOrder() == currentLine.getOrder() + 1)
                        .findFirst()
                        .orElse(null);
                /* 결재자의 정보 확인 */
//                if (currentLine.getEmployee() != currentEmployee) try {
//                    throw new Exception();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                /* 현재 라인 승인으로 처리 */
                currentLine.processApproval(ApproveType.APPROVE, documentRequest.getComment());
                /* 다음 라인이 null 이 아닐 경우 */
                if (nextLine != null) {
                    /* 예정 -> 대기로 변경 */
                    nextLine.changeStatus(ApproveType.PENDING);
                } else {
                    /* null 일 경우 결재 서류 상태를 진행 -> 승인으로 변경 */
                    document.processApproval(ApprovalStatus.IN_PROGRESS);
                }
                approvalDocumentRepository.save(document);
                break;
            }
            /* 대결 처리 과정 */
            case 3: {
                /* 현재 결재 라인 */
                ApprovalLine currentLine = approvalLineList.stream()
                        .filter(line -> line.getId() == documentRequest.getApprovalLineId())
                        .findFirst()
                        .orElse(null);
                /* 다음 결재 라인 */
                ApprovalLine nextLine = approvalLineList.stream()
                        .filter(line -> line.getOrder() == currentLine.getOrder() + 1)
                        .findFirst()
                        .orElse(null);
                /* 현재 결재 라인의 결재자를 로그인된 사원으로 변경 (대결권) */
                currentLine.changeEmployee(currentEmployee);
                /* 상태를 대결로 변경 */
                currentLine.processApproval(ApproveType.DELEGATED_APPROVE, documentRequest.getComment());
                /* 다음 라인이 null 이 아닐 경우 */
                if (nextLine != null) {
                    /* 예정 -> 대기로 변경 */
                    nextLine.changeStatus(ApproveType.PENDING);
                } else {
                    /* null 일 경우 결재 서류 상태를 진행 -> 승인으로 변경 */
                    document.processApproval(ApprovalStatus.IN_PROGRESS);
                }
                approvalDocumentRepository.save(document);
                break;
            }
            /* 선결처리 과정 */
            case 4: {
                /* 선결처리 하려는 라인 */
                ApprovalLine currentLine = approvalLineList.stream()
                        .filter(line -> line.getEmployee() == currentEmployee)
                        .findFirst()
                        .orElse(null);
                /* 예정, 대기중인 결재라인 리스트 */
                List<ApprovalLine> pendingAndScheduledList = approvalLineList.stream()
                        .filter(line -> line.getStatus() == ApproveType.PENDING
                                || line.getStatus() == ApproveType.SCHEDULED
                                && line.getOrder() < currentLine.getOrder())
                        .toList();
                /* 다음 결재 라인 */
                ApprovalLine nextLine = approvalLineList.stream()
                        .filter(line -> line.getOrder() == currentLine.getOrder() + 1)
                        .findFirst()
                        .orElse(null);
                /* 선결하려는 결재 라인 선결처리 */
                currentLine.processApproval(ApproveType.APPROVE.PRE_APPROVE, documentRequest.getComment());
                /* 대기, 예정중인 결재선 모두 후열처리 */
                pendingAndScheduledList.forEach(line-> line.changeStatus(ApproveType.CONFIRM_ONLY));
                /* 다음 라인이 null 이 아닐 경우 */
                if (nextLine != null) {
                    /* 예정 -> 대기로 변경 */
                    nextLine.changeStatus(ApproveType.PENDING);
                } else {
                    /* null 일 경우 결재 서류 상태를 진행 -> 승인으로 변경 */
                    document.processApproval(ApprovalStatus.IN_PROGRESS);
                }
                approvalDocumentRepository.save(document);
                break;
            }
            /* 전결 처리 과정 */
            case 5: {
                /* 전결처리 할 결재선 */
                ApprovalLine currentLine =  approvalLineList.stream()
                        .filter(line -> line.getEmployee() == currentEmployee)
                        .findFirst()
                        .orElse(null);
                /* 승인처리 되지 않은 모든 대기, 예정 리스트 (현재 라인 제외) */
                List<ApprovalLine> pendingAndScheduledList = approvalLineList.stream()
                        .filter(line -> (line.getStatus() == ApproveType.PENDING
                                || line.getStatus() == ApproveType.SCHEDULED)
                                && line.getOrder() != currentLine.getOrder())
                        .toList();
                System.out.println("test" + approvalLineList.get(0).getStatus() + ApproveType.PENDING);
                /* 전결 처리 */
                currentLine.processApproval(ApproveType.ALL_APPROVE, documentRequest.getComment());
                /* 대기, 예정 상태의 리스트를 모두 후열처리 */
                pendingAndScheduledList.forEach(line-> line.changeStatus(ApproveType.CONFIRM_ONLY));
                /* 결재 상태를 승인으로 변경 */
                document.processApproval(ApprovalStatus.IN_PROGRESS);
                approvalDocumentRepository.save(document);
                break;
            }
            case 6: {
                /* 반려처리 할 결재선 */
                ApprovalLine currentLine = approvalLineList.stream()
                        .filter(line -> line.getId() == documentRequest.getApprovalLineId())
                        .findFirst()
                        .orElse(null);
                /* 이후의 결재선 */
                List<ApprovalLine> afterLineList = approvalLineList.stream()
                        .filter(line->line.getOrder() > currentLine.getOrder())
                        .toList();

                /* 결재선 반려처리 */
                currentLine.processApproval(ApproveType.DENY, documentRequest.getComment());
                /* 결재 서류 반려처리 */
                document.processApproval(ApprovalStatus.DENY);
                /* 이후 결재선 null 값 처리 */
                afterLineList.forEach(line-> line.changeStatus(ApproveType.DENY_AFTER));

                approvalDocumentRepository.save(document);
                break;
            }
        }
    }

    public void deleteApprovalDocument(int documentId) {
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        ApprovalDocument document = approvalDocumentRepository.findById(documentId).orElse(null);
        if (employee != document.getDraftEmployee()) {
            throw new RuntimeException("기안자 정보가 일치하지 않습니다.");
        }
        if (document == null) {
            throw new RuntimeException("서류가 존재하지 않습니다.");
        }
        if (document.getApprovalLineList().get(0).getStatus() != ApproveType.PENDING){
            throw new RuntimeException("결재된 서류는 수정할 수 없습니다.");
        }
        approvalDocumentRepository.deleteById(documentId);
    }
}
