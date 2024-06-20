package com.errorCode.pandaOffice.common.exception;


import com.errorCode.pandaOffice.common.exception.type.ExceptionCode;
import lombok.Getter;

@Getter
public class ConflictException extends CustomException {
    public ConflictException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
