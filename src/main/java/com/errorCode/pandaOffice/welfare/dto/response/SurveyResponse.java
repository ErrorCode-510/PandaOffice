package com.errorCode.pandaOffice.welfare.dto.response;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class SurveyResponse {
    private int id;//ID설정(활성화된 설문 자동 매핑)
    private String title;
    private String categoryName;
    private String startDate;
    private String endDate;

    public static SurveyResponse of(Survey surveyEntity) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.id = surveyEntity.getId(); // ID 설정
        surveyResponse.title = surveyEntity.getTitle();
        surveyResponse.categoryName = surveyEntity.getCategory().getCategoryName();
        surveyResponse.startDate = formatDate(surveyEntity.getStartDate());
        surveyResponse.endDate = formatDate(surveyEntity.getEndDate());
        return surveyResponse;
    }

    private static String formatDate(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);
        }
        return null;
    }
}
