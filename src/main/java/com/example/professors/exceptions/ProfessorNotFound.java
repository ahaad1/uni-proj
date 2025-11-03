package com.example.professors.exceptions;

import com.example.common.enums.ResponseStatus;
import com.example.common.exceptions.BaseException;

public class ProfessorNotFound extends BaseException {
    public ProfessorNotFound(String message) {
        super(ResponseStatus.NOT_FOUND, message);
    }
}
