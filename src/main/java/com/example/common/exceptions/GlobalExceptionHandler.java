package com.example.common.exceptions;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        log.info("exception instanse: {}", String.valueOf(exception));

        if(exception instanceof BaseException baseException) {
            return Response.ok(new ResponseData<>(baseException.getStatus(), exception.getMessage())).build();
        }

        log.info("Unexpected exception caught", exception);
        return Response.ok(new ResponseData<>(ResponseStatus.GLOBAL_ERROR, "An unexpected error occurred")).build();
    }
}
