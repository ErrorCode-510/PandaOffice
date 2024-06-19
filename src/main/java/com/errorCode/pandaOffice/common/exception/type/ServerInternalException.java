package com.errorCode.pandaOffice.common.exception.type;
import java.io.Serial;

public class ServerInternalException extends CustomException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServerInternalException(String message) {
        super(message);
    }
}
