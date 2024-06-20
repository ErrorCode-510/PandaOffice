package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.UpdateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

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
        /* ok 메소드는 객체 반환하므로 build 생략. 파라미터는 반환 response body */
        return ResponseEntity.ok(documentDetailResponse);
    }
    /**
     * 새로운 결재 문서를 등록한다.
     *
     * @author 편승준
     * @version 1.0
     * @since 2024-06-19
     * @param documentRequest, multiFile 새로운 결재 문서 생성 요청 객체
     * @return ResponseEntity<Void> 생성된 문서의 리다이렉트 URI 를 포함하는 응답 객체
     */
    @PostMapping("approval-document")
    public ResponseEntity<Void> createApprovalDocument(
            @RequestPart final CreateApprovalDocumentRequest documentRequest,
            @RequestPart(required = false) final List<MultipartFile> attachedFile){
        final DocumentTemplate documentTemplate = documentTemplateRepository.findById(documentRequest.getDocumentTemplateId())
                .orElseThrow(); /* 익셉션 등록 필요 */
        final int documentId = approvalDocumentService.createApprovalDocument(documentRequest, attachedFile);
        /* 상태코드 201, 문자열 URI 로 반환, ResponseEntity 빌드 */
        return ResponseEntity.created(URI.create("/approval-document" + documentId)).build();
    }
    /**
     * 생성된 문서를 결재처리 한다.
     * 결재 처리 종류에 따라 수정되는 사항이 달라진다.
     * @author*/
    @PutMapping("approval-document")
    public ResponseEntity<Void> updateApprovalDocument(@RequestBody UpdateApprovalDocumentRequest documentRequest){
        return ResponseEntity.noContent().build();
    }

    /**
     * 아직 결재처리 되지 않은 서류를 삭제할 수 있다.
     * */
    @DeleteMapping
    public ResponseEntity<Void> deleteApprovalDocument(){
         return null;
    }
}