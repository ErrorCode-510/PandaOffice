package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ApprovalDocumentController {
    private final ApprovalDocumentService approvalDocumentService;
    private final DocumentTemplateRepository documentTemplateRepository;

    /* 결재 문서 관리 */
    @GetMapping("/approval-document")
    public ResponseEntity<PagingResponse> getApprovalDocuments(
            /* 기안일 조건 (min) */
            @RequestParam(required = false) final LocalDate startDate,
            /* 기안일 조건 (max) */
            @RequestParam(required = false) final LocalDate endDate,
            /* 양식 ID */
            @RequestParam(required = false) final Integer templateId,
            /* 제목 */
            @RequestParam(required = false) final String title,
            /* 기안자 이름 */
            @RequestParam(required = false) final String draftEmployeeName,
            /* 결재 상태 (0 = 승인, 1 = 진행중, 2 = 반려) */
            @RequestParam(required = false) final Integer status,
            /* 현재 페이지 */
            @RequestParam(required = false) final Integer nowPage,
            /* 한 페이지 서류 량 */
            @RequestParam(required = false) final Integer pageSize
    ) {
        /**
         * 검색 조건에 맞는 서류들을 반환한다.
         * @version : 1
         * @author : 편승준
         * @param : 검색 조건들. null 이 가능하다
         * @return : 응답을 담은 Page 객체
         * */
        final Page<ApprovalDocumentListResponse> documents = approvalDocumentService.searchDocuments(
                startDate,
                endDate,
                templateId,
                title,
                draftEmployeeName,
                status,
                nowPage,
                pageSize
        );
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(documents);
        final PagingResponse pagingResponse = PagingResponse.of(documents.getContent(), pagingButtonInfo);
        System.out.println(documents);

        return ResponseEntity.ok(pagingResponse);
    }
    /**
     * 문서 상세보기 시 값을 반환해준다.
     * @author: 편승준
     * @param : 문서 ID
     * @return : ApprovalDocumentDetailResponse (미완성)
     * */
    @GetMapping("approval-document/{documentId}")
    public ResponseEntity<ApprovalDocumentDetailResponse> getApprovalDocumentDetail(@PathVariable int documentId){
        final ApprovalDocumentDetailResponse documentDetailResponse = approvalDocumentService.getDocumentDetail(documentId);
        return ResponseEntity.ok(documentDetailResponse);
    }
    @PostMapping("approval-document")
    public ResponseEntity<Void> createApprovalDocument(@RequestBody CreateApprovalDocumentRequest documentRequest){
        final DocumentTemplate documentTemplate = documentTemplateRepository.findById(documentRequest.getDocumentTemplateId())
                .orElseThrow(); /* 익셉션 등록 필요 */
        final Long documentId = approvalDocumentService.createApprovalDocument(documentRequest);
        return null;
    }
}