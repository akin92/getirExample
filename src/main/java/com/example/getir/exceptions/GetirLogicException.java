package com.example.getir.exceptions;

import com.example.getir.enums.ErrorCode;

public class GetirLogicException extends  Exception{
    private ErrorCode errorCode;

    public GetirLogicException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    public GetirLogicException(String errorMessage) {
        super(errorMessage);
    }

    public GetirLogicException(String errorMessage, ErrorCode errorCodes) {
        super(errorMessage);
        this.errorCode = errorCodes;
    }

    public GetirLogicException(String errorMessage, Throwable cause , ErrorCode errorCodes) {
        super(errorMessage,cause);
        this.errorCode = errorCodes;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
