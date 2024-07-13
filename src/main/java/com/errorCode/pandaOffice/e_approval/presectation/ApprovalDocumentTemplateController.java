package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request.*;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.ApprovalDocumentFolderResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.ApprovalDocumentTemplateResponse;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.CreateTemplateResponse;
import com.errorCode.pandaOffice.e_approval.service.ApprovalDocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        System.out.println(123);
        List<ApprovalDocumentFolderResponse> response = approvalDocumentTemplateService.getAllApprovalDocumentTemplateFolder();
        return ResponseEntity.ok(response);
    }
    @PostMapping("approval-document-template-folder")
    public ResponseEntity<ApprovalDocumentFolderResponse> createApprovalDocumentTemplateFolder(@RequestBody CreateApprovalDocumentFolderRequest request){
        ApprovalDocumentFolderResponse response = approvalDocumentTemplateService.createNewFolder(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("approval-document-template-status")
    public ResponseEntity<ApprovalDocumentFolderResponse> modifyApprovalDocumentTemplateStatus(@RequestBody UpdateDocumentTemplateStatusRequest requests){
        ApprovalDocumentFolderResponse response = approvalDocumentTemplateService.updateTemplateStatus(requests);
        return ResponseEntity.ok(response);
    }

    @PutMapping("approval-document-template/ref-folder")
    public ResponseEntity<ApprovalDocumentFolderResponse> modifyApprovalDocumentTemplateRefFolder(@RequestBody UpdateDocumentTemplateRefFolderRequest request){
        ApprovalDocumentFolderResponse response = approvalDocumentTemplateService.updateTemplateRefFolder(request);
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


    @GetMapping ("approval-document-template/new")
    public ResponseEntity<CreateTemplateResponse> getInformationForNewTemplate(){
        CreateTemplateResponse response = approvalDocumentTemplateService.getInformationForNewTemplate();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("approval-document-template/{templateId}")
    public ResponseEntity<ApprovalDocumentTemplateResponse> getApprovalDocumentTemplate(@PathVariable int templateId){
        ApprovalDocumentTemplateResponse response = approvalDocumentTemplateService.getApprovalDocumentTemplate(templateId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("approval-document-template")
    public ResponseEntity<Void> createApprovalDocumentTemplate(@RequestBody final CreateApprovalDocumentTemplateRequest request){
        System.out.println(request);
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
