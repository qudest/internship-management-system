package com.ds.ims.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса JWT
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequestDto {
    @ApiModelProperty("Логин пользователя")
    String username;
    @ApiModelProperty("Пароль пользователя")
    String password;
}
