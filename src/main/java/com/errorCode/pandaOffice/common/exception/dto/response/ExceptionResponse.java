package com.errorCode.pandaOffice.common.exception.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ExceptionResponse {
    private String message;
    private String details;

    public ExceptionResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse() {

    }

    public void setTimestamp(LocalDate now) {
    }
}
