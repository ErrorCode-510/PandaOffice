package com.errorCode.pandaOffice.common.exception.type;
import java.io.Serial;

public class NotFoundException extends CustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
