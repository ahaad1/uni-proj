package com.example.students.exceptions;

import com.example.common.enums.ResponseStatus;
import com.example.common.exceptions.BaseException;

public class StudentNotFound extends BaseException {
    public StudentNotFound(String message) {
        super(ResponseStatus.NOT_FOUND, message);
    }
}

