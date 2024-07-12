package com.errorCode.pandaOffice.welfare.dto.request;

import java.util.List;

public class SurveyQuestionRequest {
    private String title;
    private List<Integer> options;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }
}
