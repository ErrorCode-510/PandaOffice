package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.auth.type.CustomUser;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentListResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.CreateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.e_approval.dto.ApprovalDocument.UpdateApprovalDocumentRequest;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApprovalDocumentController {
    private final ApprovalDocumentService approvalDocumentService;

    /**
     * 결재 문서를 검색하는 api
     * <p>
     * 서비스에 파라미터와 DB 탐색을 위임하는 메소드
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
     * @return ResponseEntity.ok(조건에 따라 검색된 response)
     * @version 1
     * @see com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus status ENUM
     */
    @GetMapping("/approval-document")
    public ResponseEntity<PagingResponse> getApprovalDocuments(
            @RequestParam(required = false) final LocalDate startDate,
            @RequestParam(required = false) final LocalDate endDate,
            @RequestParam(required = false) final Integer templateId,
            @RequestParam(required = false) final String title,
            @RequestParam(required = false) final String draftEmployeeName,
            @RequestParam(required = false) final Integer status,
            @RequestParam(required = false) final Integer nowPage,
            @RequestParam(required = false) final Integer pageSize
    ) {
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

        return ResponseEntity.ok(pagingResponse);
    }

    /**
     * 문서 상세보기 시 값을 반환해준다
     * <p>
     * 문서를 보여주는데 필요한 데이터들을 가져온다.
     * </p>
     *
     * @param documentId 조회할 문서의 ID
     * @return ApprovalDocumentDetailResponse (미완성)
     * @author 편승준
     */
    @GetMapping("approval-document/{documentId}")
    public ResponseEntity<ApprovalDocumentDetailResponse> getApprovalDocumentDetail(@PathVariable int documentId) {
        final ApprovalDocumentDetailResponse documentDetailResponse = approvalDocumentService.getDocumentDetail(documentId);
        /* ok 메소드는 객체 반환하므로 build 생략. 파라미터는 반환 response body */
        return ResponseEntity.ok(documentDetailResponse);
    }


    /**
     * 새로운 결재 문서를 등록한다.
     *
     * @param documentRequest 문서 등록에 필요한 request
     *                        <ul>
     *                          <li><code>title</code> 제목</li>
     *                          <li><code>documentTemplateId</code> 문서 템플릿 코드</li>
     *                          <li><code>document</code> 문서 내용</li>
     *                          <li><code>approvalLineList</code> 결재선 리스트</li>
     *                        </ul>
     * @param attachedFile    첨부파일
     * @return 등록된 문서의 URI
     * @author 편승준
     * @version 1.0
     */
    @PostMapping("approval-document")
    public ResponseEntity<Void> createApprovalDocument(
            @RequestPart final CreateApprovalDocumentRequest documentRequest,
            @RequestPart(required = false) final List<MultipartFile> attachedFile) {
        final int documentId = approvalDocumentService.createApprovalDocument(documentRequest, attachedFile);
        /* 상태코드 201, 문자열 URI 로 반환, ResponseEntity 빌드 */
        return ResponseEntity.created(URI.create("/approval-document/" + documentId)).build();
    }

    /**
     * 생성된 문서를 결재처리 한다.
     * <p>
     * 결재 처리 종류에 따라 수정되는 사항이 달라진다.
     * </p>
     * @param documentRequest 결재선 및 결재 서류 처리에 필요한 request
     *                        <Ul>
     *                          <li><code>documentId</code>문서 ID(NotNull)</li>
     *                          <li><code>approvalLineId</code>결재선 ID</li>
     *                          <li><code>type</code>결재 분류 (1~9)</li>
     *                          <li><code>date</code>결재일 (자동으로 초기화)</li>
     *                          <li><code>comment</code>결재와 함께 오는 코멘트</li>
     *                        </Ul>
     * @author
     */
    @PutMapping("approval-document")
    public ResponseEntity<Void> updateApprovalDocument(@RequestBody UpdateApprovalDocumentRequest documentRequest) {
        approvalDocumentService.updateApprovalDocument(documentRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * 아직 결재처리 되지 않은 서류를 삭제할 수 있다.
     * @param documentId 서류의 ID
     */
    @DeleteMapping("approval-document/{documentId}")
    public ResponseEntity<Void> deleteApprovalDocument(@PathVariable int documentId) {
        approvalDocumentService.deleteApprovalDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}