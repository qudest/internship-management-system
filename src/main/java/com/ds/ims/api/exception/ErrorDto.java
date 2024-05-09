package com.ds.ims.api.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO для ошибки
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {
    int status;
    String message;
    LocalDateTime timestamp;

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
