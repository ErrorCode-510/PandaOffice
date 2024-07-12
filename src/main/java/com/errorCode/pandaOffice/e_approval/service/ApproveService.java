package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalDocument;
import com.errorCode.pandaOffice.e_approval.domain.entity.ApprovalLine;
import com.errorCode.pandaOffice.e_approval.domain.repository.ApprovalDocumentRepository;
import com.errorCode.pandaOffice.e_approval.domain.repository.DocumentTemplateRepository;
import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.request.UpdateApprovalDocumentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApproveService {
    private final ApprovalDocumentRepository approvalDocumentRepository;
    public void approve(UpdateApprovalDocumentRequest documentRequest) {
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();
        document.getApprovalLineList()
                .stream()
                .filter(line->line.getOrder() == documentRequest.getOrder())
                .findFirst()
                .ifPresent(line->{
                    line.changeStatus(ApproveType.APPROVE);
                    line.changeHandling(documentRequest.getDate());
                });
        document.getApprovalLineList()
                .stream()
                .filter(line -> line.getOrder() == documentRequest.getOrder() + 1)
                .findFirst()
                .ifPresentOrElse(
                        nextLine -> nextLine.changeStatus(ApproveType.PENDING),
                        () -> {
                            document.stateHandler(ApprovalStatus.APPROVE);
                        }
                );
        approvalDocumentRepository.save(document);
    }
    public void deny(UpdateApprovalDocumentRequest documentRequest) {
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();
        document.getApprovalLineList()
                .stream()
                .filter(line->line.getOrder() == documentRequest.getOrder())
                .findFirst()
                .ifPresent(line->{
                    line.changeStatus(ApproveType.DENY);
                    line.changeHandling(documentRequest.getDate());
                });

        document.getApprovalLineList()
                .stream()
                .filter(line -> line.getOrder() > documentRequest.getOrder())
                .forEach(line->line.changeStatus(ApproveType.DENY_AFTER));
        document.stateHandler(ApprovalStatus.DENY);
        approvalDocumentRepository.save(document);
    }
    public void preApprove(UpdateApprovalDocumentRequest documentRequest) {
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();
        document.getApprovalLineList()
                .stream()
                .filter(line->line.getOrder() == documentRequest.getOrder())
                .findFirst()
                .ifPresent(line->{
                    line.changeStatus(ApproveType.PRE_APPROVE);
                    line.changeHandling(documentRequest.getDate());
                });

        document.getApprovalLineList()
                .stream()
                .filter(line -> line.getOrder() < documentRequest.getOrder()
                        && (line.getStatus() == ApproveType.PENDING || line.getStatus() == ApproveType.SCHEDULED))
                .findFirst()
                .ifPresentOrElse(
                        nextLine -> nextLine.changeStatus(ApproveType.AFTER_APPROVE),
                        () -> {
                            document.stateHandler(ApprovalStatus.APPROVE);
                        }
                );

        approvalDocumentRepository.save(document);
    }
    public void allApprove(UpdateApprovalDocumentRequest documentRequest) {
        ApprovalDocument document = approvalDocumentRepository.findById(documentRequest.getDocumentId())
                .orElseThrow();
        document.getApprovalLineList()
                .stream()
                .filter(line->line.getOrder() == documentRequest.getOrder())
                .findFirst()
                .ifPresent(line->{
                    line.changeStatus(ApproveType.ALL_APPROVE);
                    line.changeHandling(documentRequest.getDate());
                });

        document.getApprovalLineList()
                .stream()
                .filter(line -> line.getOrder() != documentRequest.getOrder()
                        && (line.getStatus() == ApproveType.PENDING || line.getStatus() == ApproveType.SCHEDULED))
                .findFirst()
                .ifPresent(nextLine -> nextLine.changeStatus(ApproveType.AFTER_APPROVE));
        document.stateHandler(ApprovalStatus.APPROVE);
        approvalDocumentRepository.save(document);
    }
}
