package com.errorCode.pandaOffice.welfare.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ReplyRecordRequest {
    private int employeeId; // 사번
    private int surveyId; // 설문코드
    private int stAgree; // 매우 그렇다 합계
    private int agree; // 그렇다 합계
    private int average; // 보통이다 합계
    private int disagree; // 약간 아니다 합계
    private int stDisagree; // 매우 아니다 합계


}