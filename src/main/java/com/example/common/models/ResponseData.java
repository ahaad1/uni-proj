package com.example.common.models;

import com.example.common.enums.ResponseStatus;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseData<T> {
//    private ResponseStatus status = ResponseStatus.SUCCESS;
    private T data;
    private LocalDateTime time = LocalDateTime.now();
    private String message;
    private Integer code;
    private Long executionTimeMs;
    private String requestId;

    public ResponseData(@NotNull ResponseStatus status) {
        this.data = null;
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public ResponseData(@NotNull ResponseStatus status, String message) {
        this.data = null;
        this.message = message;
        this.code = status.getCode();
    }

    public ResponseData(T data, @NotNull ResponseStatus status) {
        this.data = data;
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public ResponseData(T data, @NotNull ResponseStatus status, String message) {
        this.data = data;
        this.code = status.getCode();
        this.message = message;
    }

    public static <T> ResponseDataBuilder<T> builder() {
        return new ResponseDataBuilder<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ResponseDataBuilder<T> {
        private T data;
        private ResponseStatus status = ResponseStatus.SUCCESS;
        private String message;
        private Long executionTimeMs;
        private String requestId;

        public ResponseDataBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDataBuilder<T> status(ResponseStatus status) {
            this.status = status;
            return this;
        }

        public ResponseDataBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseDataBuilder<T> executionTimeMs(Long executionTimeMs) {
            this.executionTimeMs = executionTimeMs;
            return this;
        }

        public ResponseDataBuilder<T> requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public ResponseData<T> build() {
            ResponseData<T> response = new ResponseData<>();
            response.setData(data);
            response.setCode(status.getCode());
            response.setMessage(message != null ? message : status.getMessage());
            response.setExecutionTimeMs(executionTimeMs);
            response.setRequestId(requestId);
            return response;
        }

    }
}
