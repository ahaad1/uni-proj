package com.example.disciplines.exceptions;

import com.example.common.enums.ResponseStatus;
import com.example.common.exceptions.BaseException;

public class DisciplineNotFound extends BaseException {
    public DisciplineNotFound(String message) {
        super(ResponseStatus.NOT_FOUND, message);
    }
}
