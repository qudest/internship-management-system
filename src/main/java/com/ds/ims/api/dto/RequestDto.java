package com.ds.ims.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса на стажировку
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long id;
    UserDto user;
    String status;
}
