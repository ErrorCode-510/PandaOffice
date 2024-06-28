package com.errorCode.pandaOffice.welfare.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class CreateSurveyRequest {
    private final String title;
    private final int categoryId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Question> question;



    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class Question{
        private final int order;
        private final String question;
    }
}
