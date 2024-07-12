package com.errorCode.pandaOffice.e_approval.domain.type;

import com.errorCode.pandaOffice.e_approval.dto.approvalDocument.response.ApprovalDocumentDetailResponse;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApproveTypeCalculator {
    public List<ApprovalDocumentDetailResponse.ApproveTypeMap> calcAble(ApproveType status, Job job) {
        List<ApprovalDocumentDetailResponse.ApproveTypeMap> list = new ArrayList<>();
        final String jobTitle = job.getTitle();

        /* 전결권 여부 */
        boolean allApprovalAble = false;
        if(jobTitle.equals("사장")) allApprovalAble = true;
        if(jobTitle.equals("부장")) allApprovalAble = true;

        /* 결재 권한 할당 */
        if(status == ApproveType.SCHEDULED){
            list.add(convertResult(ApproveType.PRE_APPROVE));
            list.add(convertResult(ApproveType.DENY));
            if(allApprovalAble)list.add(convertResult(ApproveType.ALL_APPROVE));
        }
        if(status == ApproveType.PENDING){
            list.add(convertResult(ApproveType.APPROVE));
            list.add(convertResult(ApproveType.DENY));
            if(allApprovalAble)list.add(convertResult(ApproveType.ALL_APPROVE));
        }

        return list;
    }

    public ApprovalDocumentDetailResponse.ApproveTypeMap convertResult(ApproveType approveType) {
        return ApprovalDocumentDetailResponse
                .ApproveTypeMap
                .builder()
                .value(approveType.getValue())
                .name(approveType.getDescription())
                .build();
    }
}
