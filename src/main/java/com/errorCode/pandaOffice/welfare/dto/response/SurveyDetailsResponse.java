package com.errorCode.pandaOffice.welfare.dto.response;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import lombok.Getter;

import java.util.List;

@Getter
public class SurveyDetailsResponse {
    private int id;
    private String title;
    private List<SurveyQuestionDTO> questions;
    private List<ReplyRecordDTO> replyRecords;

    public SurveyDetailsResponse(Survey survey, List<SurveyQuestionDTO> questions, List<ReplyRecordDTO> replyRecords) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.questions = questions;
        this.replyRecords = replyRecords;
    }
}
