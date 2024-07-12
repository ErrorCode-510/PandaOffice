    package com.errorCode.pandaOffice.e_approval.service;

    import com.errorCode.pandaOffice.auth.util.TokenUtils;
    import com.errorCode.pandaOffice.e_approval.domain.entity.AutoApprovalLine;
    import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplate;
    import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplateFolder;
    import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateFolderRepository;
    import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
    import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.request.*;
    import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.ApprovalDocumentFolderResponse;
    import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.ApprovalDocumentTemplateResponse;
    import com.errorCode.pandaOffice.e_approval.dto.approvalDocumentTemplate.response.CreateTemplateResponse;
    import com.errorCode.pandaOffice.employee.domain.entity.Department;
    import com.errorCode.pandaOffice.employee.domain.entity.Employee;
    import com.errorCode.pandaOffice.employee.domain.entity.Job;
    import com.errorCode.pandaOffice.employee.domain.repository.DepartmentRepository;
    import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
    import com.errorCode.pandaOffice.employee.domain.repository.JobRepository;
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
        private final JobRepository jobRepository;
        private final DepartmentRepository departmentRepository;


        public ApprovalDocumentTemplateResponse getApprovalDocumentTemplate(int templateId) {
            final Employee draftEmployee = employeeRepository.findById(TokenUtils.getEmployeeId())
                    .orElseThrow();
            final DocumentTemplate entity = documentTemplateRepository.findById(templateId)
                    .orElseThrow();
            final List<Employee> employeeList = employeeRepository.findAll();
            final List<Department> departmentList = departmentRepository.findAll();
            final List<Job> jobList = jobRepository.findAll();
            final ApprovalDocumentTemplateResponse response = ApprovalDocumentTemplateResponse.of(entity, draftEmployee, employeeList, departmentList, jobList);
            return response;
        }

        /* ==================================================================================== */
        /* ====================================== folder ====================================== */
        /* ==================================================================================== */

        public List<ApprovalDocumentFolderResponse> getAllApprovalDocumentTemplateFolder() {
            /* 모든 폴더, 템플릿 불러오기 */
            List<DocumentTemplate> templateEntityList = documentTemplateRepository.findAll();
            List<DocumentTemplateFolder> templateFolderEntityList = documentTemplateFolderRepository.findAll();
            List<ApprovalDocumentFolderResponse> response = templateFolderEntityList
                    .stream()
                    .map(folderEntity->{
                        List<DocumentTemplate> templates = templateEntityList
                                .stream()
                                .filter(tempEntity->tempEntity.getFolderId() == folderEntity.getId())
                                .collect(Collectors.toList());
                        return ApprovalDocumentFolderResponse.of(folderEntity, templates);
                    })
                    .collect(Collectors.toList());
            return response;
        }

        public ApprovalDocumentFolderResponse createNewFolder(CreateApprovalDocumentFolderRequest request) {
            DocumentTemplateFolder newFolder = DocumentTemplateFolder.of(request);
            documentTemplateFolderRepository.save(newFolder);
            List<DocumentTemplate> templateList = documentTemplateRepository.findByFolderId(newFolder.getId());
            return ApprovalDocumentFolderResponse.of(newFolder, templateList);
        }

        public ApprovalDocumentFolderResponse modifyFolder(int folderId, String newName) {
            DocumentTemplateFolder currenFolder = documentTemplateFolderRepository.findById(folderId)
                    .orElseThrow();
            currenFolder.modifyName(newName);
            documentTemplateFolderRepository.save(currenFolder);
            return null;
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


        public ApprovalDocumentFolderResponse updateTemplateStatus(UpdateDocumentTemplateStatusRequest requests) {
            List<DocumentTemplate> templateEntityList = documentTemplateRepository.findAllById(requests.getDocumentIdList());
            Employee lastEditor = employeeRepository.findById(TokenUtils.getEmployeeId())
                    .orElseThrow();
            templateEntityList.forEach(entity -> entity.updateStatus(requests.isStatus(), lastEditor));
            documentTemplateRepository.saveAll(templateEntityList);
            DocumentTemplateFolder folder = documentTemplateFolderRepository.findById(requests.getFolderId())
                    .orElseThrow();
            List<DocumentTemplate> templates = documentTemplateRepository.findByFolderId(requests.getFolderId());
            return ApprovalDocumentFolderResponse.of(folder, templates);
        }

        public CreateTemplateResponse getInformationForNewTemplate() {
            Employee draftEmployee = employeeRepository.findById(TokenUtils.getEmployeeId())
                            .orElseThrow();
            List<Job> jobEntityList = jobRepository.findAll();
            List<Department> departmentEntityList = departmentRepository.findAll();
            List<Employee> employeeEntityList = employeeRepository.findAll();
            CreateTemplateResponse response = CreateTemplateResponse.of(draftEmployee, jobEntityList, departmentEntityList, employeeEntityList);
            return response;
        }

        public ApprovalDocumentFolderResponse updateTemplateRefFolder(UpdateDocumentTemplateRefFolderRequest request) {
            List<DocumentTemplate> templateEntityList = documentTemplateRepository.findAllById(request.getTemplateIdList());
            templateEntityList.stream().forEach(entity->entity.updateRefFolder(request.getAfterFolderId()));
            documentTemplateRepository.saveAll(templateEntityList);
            DocumentTemplateFolder folder = documentTemplateFolderRepository.findById(request.getBeforeFolderId())
                    .orElseThrow();
            List<DocumentTemplate> templates = documentTemplateRepository.findByFolderId(request.getBeforeFolderId());
            return ApprovalDocumentFolderResponse.of(folder, templates);
        }
    }
