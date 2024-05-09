package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для регистрации аккаунта
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationAccountDto {
    @ApiModelProperty("Логин пользователя")
    String username;
    @ApiModelProperty("Пароль пользователя")
    String password;
    @ApiModelProperty("Подтверждение пароля пользователя")
    @JsonProperty("confirm_password")
    String confirmPassword;
}
