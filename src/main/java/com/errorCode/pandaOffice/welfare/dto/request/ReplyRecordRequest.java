package com.errorCode.pandaOffice.welfare.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ReplyRecordRequest {
    private int employeeId; // 사번
    private int jobId;//직급번호
    private int surveyId; // 설문코드
    private int questionId;//질문문항
    private int answer; // 질문



}