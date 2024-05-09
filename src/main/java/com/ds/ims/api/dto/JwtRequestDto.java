package com.ds.ims.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса JWT
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequestDto {
    String username;
    String password;
}
