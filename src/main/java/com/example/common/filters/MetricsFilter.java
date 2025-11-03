package com.example.common.filters;

import com.example.common.models.ResponseData;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.util.UUID;

@Provider
@Priority(1)
public class MetricsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String START_TIME_PROPERTY = "startTime";
    private static final String REQUEST_ID_PROPERTY = "requestId";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty(START_TIME_PROPERTY, System.currentTimeMillis());
        requestContext.setProperty(REQUEST_ID_PROPERTY, generateRequestId());
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        Long startTime = (Long) requestContext.getProperty(START_TIME_PROPERTY);
        String requestId = (String) requestContext.getProperty(REQUEST_ID_PROPERTY);

        if (startTime != null) {
            long executionTime = System.currentTimeMillis() - startTime;

            Object entity = responseContext.getEntity();
            if (entity instanceof ResponseData<?> responseData) {
                responseData.setExecutionTimeMs(executionTime);
                responseData.setRequestId(requestId);
            }
        }
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}
