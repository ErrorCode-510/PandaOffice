package com.errorCode.pandaOffice.common.exception;


import com.errorCode.pandaOffice.common.exception.type.ExceptionCode;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {
    public NotFoundException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
