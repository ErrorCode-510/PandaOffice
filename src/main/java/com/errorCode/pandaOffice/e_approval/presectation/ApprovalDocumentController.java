package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.repository.ApprovalDocumentSpecification;
import com.errorCode.pandaOffice.e_approval.dto.response.ApproveDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentService;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApprovalDocumentController {
    private final ApprovalDocumentService approvalDocumentService;

    /* 결재 문서 가져오는 메소드 */
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
            /* 결재 상태 (APPROVE, PROGRESS, REJECTION) */
            @RequestParam(required = false) final String status,
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
        final Page<ApproveDocumentListResponse> documents = approvalDocumentService.searchDocuments(
                startDate,
                endDate,
                templateId,
                title,
                draftEmployeeName,
                status,
                pageSize
        );
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(documents);
        final PagingResponse pagingResponse = PagingResponse.of(documents.getContent(), pagingButtonInfo);
        System.out.println(documents);

        return ResponseEntity.ok(pagingResponse);
    }
}