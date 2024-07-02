package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.*;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApprovalDocumentTemplateController {
    private final ApprovalDocumentTemplateService approvalDocumentTemplateService;


    /**
     * 결재 템플릿의 폴더를 만들기 위한 정보를 가져오기 위한 요청
     *
     * @return <ul>
     *     <li><code>folderId</code>>폴더의 ID</li>
     *     <li><code>name</code>폴더명</li>
     *     <li><code>subFolderList</code>하위 폴더의 리스트
     *     <li><code>documentList</code>하위 양식의 리스트</li>
     *     <ul>
     *         <li><code>documentId</code>양식의 ID</li>
     *         <li><code>title</code>양식의 이름</li>
     *     </ul>
     *     </li>
     * </ul>
     */
    @GetMapping("approval-document-template-folders")
    public ResponseEntity<List<ApprovalDocumentFolderResponse>> getAllApprovalDocumentTemplateFolders() {
        List<ApprovalDocumentFolderResponse> response = approvalDocumentTemplateService.getAllApprovalDocumentTemplateFolder();
        return ResponseEntity.ok(response);
    }
    @PostMapping("approval-document-template-folder")
    public ResponseEntity<Void> createApprovalDocumentTemplateFolder(@RequestBody CreateApprovalDocumentFolderRequest request){
        int folderId = approvalDocumentTemplateService.createNewFolder(request);
        return ResponseEntity.created(URI.create(folderId +"")).build();
    }
    @PutMapping("approval-document-template-status")
    public ResponseEntity<List<ApprovalDocumentFolderResponse>> modifyApprovalDocumentTemplateStatus(@RequestBody UpdateDocumentTemplateStatusRequest requests){
        approvalDocumentTemplateService.updateTemplateStatus(requests);
        List<ApprovalDocumentFolderResponse> response = approvalDocumentTemplateService.getAllApprovalDocumentTemplateFolder();
        return ResponseEntity.ok(response);
    }
    @PutMapping("approval-document-template-folder")
    public ResponseEntity<ApprovalDocumentFolderResponse> modifyApprovalDocumentTemplateFolder(@RequestParam int folderId,
                                                                     @RequestParam String newName){
        ApprovalDocumentFolderResponse currentFolder = approvalDocumentTemplateService.modifyFolder(folderId, newName);
        return ResponseEntity.ok(currentFolder);
    }
    @DeleteMapping("approval-document-template-folder/{folderId}")
    public ResponseEntity<Void> deleteApprovalDocumentTemplateFolder(@PathVariable final int folderId){
        approvalDocumentTemplateService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("approval-document-template/{templateId}")
    public ResponseEntity<ApprovalDocumentTemplateResponse> getApprovalDocumentTemplate(@PathVariable int templateId){
        /* 결재 템플릿에 대한 모든 정보 담기
        * DB 에 저장된 사원 명시 or 직급, 부서 명시를 판단하여 사원을 순서대로 나열해놓은 AutoApprovalLine 리스트가 있음 */
        ApprovalDocumentTemplateResponse response = approvalDocumentTemplateService.getApprovalDocumentTemplate(templateId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("approval-document-template")
    public ResponseEntity<Void> createApprovalDocumentTemplate(@RequestBody final CreateApprovalDocumentTemplateRequest request){
        final int templateId = approvalDocumentTemplateService.createNewApprovalDocumentTemplate(request);
        return ResponseEntity.created(URI.create("approval-document-template/" + templateId)).build();
    }
    @PutMapping("approval-document-template")
    public ResponseEntity<ApprovalDocumentTemplateResponse> updateApprovalDocumentTemplate(@RequestBody final UpdateApprovalDocumentTemplateRequest request){
        int templateId = approvalDocumentTemplateService.updateApprovalDocumentTemplate(request);
        ApprovalDocumentTemplateResponse response = approvalDocumentTemplateService.getApprovalDocumentTemplate(templateId);
        return ResponseEntity.ok(response);
    }
}
