package com.errorCode.pandaOffice.common.exception.type;

import java.io.Serial;

public class ConflictException  extends CustomException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(message);
    }
}
