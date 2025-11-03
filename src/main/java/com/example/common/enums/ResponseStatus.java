package com.example.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseStatus {
    SUCCESS(0, "Успешное выполнение"),
    BAD_REQUEST(400, "Некорректный запрос"),
    UNAUTHORIZED(401, "Неавторизованный доступ"),
    FORBIDDEN(403, "Доступ запрещен"),
    NOT_FOUND(404, "Ресурс не найден"),
    INTERNAL_ERROR(500, "Внутренняя ошибка сервера"),
    VALIDATION_ERROR(1001, "Ошибка валидации"),
    BUSINESS_ERROR(1002, "Бизнес-ошибка"),
    GLOBAL_ERROR(-1000, "Global error");


    private final Integer code;
    private final String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
