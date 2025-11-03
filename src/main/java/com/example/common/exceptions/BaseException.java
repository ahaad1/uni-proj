package com.example.common.exceptions;

import com.example.common.enums.ResponseStatus;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResponseStatus status;

    public BaseException(ResponseStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(ResponseStatus status, String message, Throwable cause) {
        super(message);
        this.status = status;
    }
}
