package com.errorCode.pandaOffice.welfare.dto.response;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SurveyResponse {
    private String title;
    private String categoryName;

    public static SurveyResponse of(Survey surveyEntity) {
        SurveyResponse surveyResponse = new SurveyResponse();

        surveyResponse.title = surveyEntity.getTitle();
        surveyResponse.categoryName = surveyEntity.getCategory().getCategoryName();

        return surveyResponse;
    }
}
