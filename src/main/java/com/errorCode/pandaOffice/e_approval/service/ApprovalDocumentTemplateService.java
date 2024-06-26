package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.AutoApprovalLine;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplateFolder;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateFolderRepository;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.*;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentTemplateService {
    private final DocumentTemplateRepository documentTemplateRepository;
    private final DocumentTemplateFolderRepository documentTemplateFolderRepository;
    private final EmployeeRepository employeeRepository;





    public ApprovalDocumentTemplateResponse getApprovalDocumentTemplate(int templateId) {
        /* 결재 양식 가져오기 */
        DocumentTemplate template = documentTemplateRepository.findById(templateId)
                .orElseThrow();
        /* 결재 양식 내 결재선 엔티티의 정보를 참고하여 결재선 + 사원 map 만들기 */
        Map<Integer, Employee> approvalLineMap = new HashMap<>();
        if(template.getAutoApprovalLines().size() != 0){
            approvalLineMap = template.getAutoApprovalLines().stream()
                    .collect(Collectors.toMap(
                            /* key = 순서 */
                            AutoApprovalLine::getOrder,
                            line->{
                                if(line.getEmployeeId() != null){
                                    /* EmployeeId 가 null 이 아닐경우 사번에 맞는 사원 할당 */
                                    return employeeRepository.findById(line.getEmployeeId())
                                            .orElseThrow();
                                } else {
                                    /* null 일 경우 부서와 직급 코드에 맞는 사원 할당 */
                                    return employeeRepository.findFirstByDepartment_IdAndJob_IdOrderByHireDateDesc(
                                                    line.getDepartmentId(),
                                                    line.getJobId())
                                            .orElseThrow();
                                }
                            }
                    ));
        } else {
            approvalLineMap = null;
        }
        /* 결재양식 entity 와 Map<Order, Employee> 형태의 map 을 전달하여 response 생성 및 반환 */
        return ApprovalDocumentTemplateResponse.of(template, approvalLineMap);
    }

    /* ==================================================================================== */
    /* ====================================== folder ====================================== */
    /* ==================================================================================== */

    public List<ApprovalDocumentFolderResponse> getAllApprovalDocumentTemplateFolder() {
        /* 모든 폴더, 템플릿 불러오기 */
        List<DocumentTemplate> templateEntityList = documentTemplateRepository.findAll();
        List<DocumentTemplateFolder> templateFolderEntityList = documentTemplateFolderRepository.findAll();


        /* 폴더 map 형태로 저장 */
        Map<Integer, ApprovalDocumentFolderResponse> folderMap = new HashMap<>();
        for (DocumentTemplateFolder folderEntity : templateFolderEntityList){
            ApprovalDocumentFolderResponse folderResponse = ApprovalDocumentFolderResponse.of(folderEntity);
            folderMap.put(folderEntity.getId(), folderResponse);
        }
        // 모든 문서를 해당 폴더에 추가
        for (DocumentTemplate documentEntity : templateEntityList) {
            ApprovalDocumentFolderResponse folder = folderMap.get(documentEntity.getFolderId());
            if (folder != null) {
                folder.getDocumentList().add(ApprovalDocumentFolderResponse.DocumentTemplateResponse.of(documentEntity));
            }
        }

        /* 계층 구조로 정렬 */
        List<ApprovalDocumentFolderResponse> rootFolders = new ArrayList<>();
        for (DocumentTemplateFolder folderEntity : templateFolderEntityList) {
            ApprovalDocumentFolderResponse folder = folderMap.get(folderEntity.getId());
            if (folderEntity.getRefFolderId() == null) {
                rootFolders.add(folder);
            } else {
                ApprovalDocumentFolderResponse refFolder = folderMap.get(folderEntity.getRefFolderId());
                if (refFolder != null) {
                    refFolder.getSubFolderList().add(folder);
                }
            }
        }

        return rootFolders;
    }

    public int createNewFolder(CreateApprovalDocumentFolderRequest request) {
        DocumentTemplateFolder newFolder = DocumentTemplateFolder.of(request);
        documentTemplateFolderRepository.save(newFolder);
        return newFolder.getId();
    }

    public int modifyFolder(int folderId, String newName) {
        DocumentTemplateFolder currenFolder = documentTemplateFolderRepository.findById(folderId)
                .orElseThrow();
        currenFolder.modifyName(newName);
        documentTemplateFolderRepository.save(currenFolder);
        return currenFolder.getId();
    }

    public void deleteFolder(int folderId) {
        List<DocumentTemplate> templates = documentTemplateRepository.findByFolderId(folderId);
        List<DocumentTemplateFolder> folders = documentTemplateFolderRepository.findByRefFolderId(folderId);
        try {
            if(templates.size() > 0 || folders.size() > 0){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DocumentTemplateFolder folder = documentTemplateFolderRepository.findById(folderId)
                        .orElseThrow();
        documentTemplateFolderRepository.delete(folder);
    }

    public int createNewApprovalDocumentTemplate(CreateApprovalDocumentTemplateRequest request) {
        List<AutoApprovalLine> lineEntityList = request.getAutoApprovalLineRequestList().stream().map(
                line->AutoApprovalLine.of(line)
        ).toList();
        int employeeId = TokenUtils.getEmployeeId();
        Employee lastEditorEntity = employeeRepository.findById(employeeId)
                .orElseThrow();
        DocumentTemplate templateEntity = DocumentTemplate.of(request, lineEntityList, lastEditorEntity);
        documentTemplateRepository.save(templateEntity);
        return templateEntity.getId();
    }

    public int updateApprovalDocumentTemplate(UpdateApprovalDocumentTemplateRequest request) {
        DocumentTemplate template = documentTemplateRepository.findById(request.getId())
                .orElseThrow();
        /* 가변/불변 주의 */
        List<AutoApprovalLine> lineEntity = request.getNewApprovalLine().stream().map(
                line->AutoApprovalLine.of(line)
        ).collect(Collectors.toList());
        template.modifyByRequest(request, lineEntity);
        documentTemplateRepository.save(template);
        return template.getId();
    }
}
