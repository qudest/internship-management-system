package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для регистрации аккаунта
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationAccountDto {
    String username;
    String password;
    @JsonProperty("confirm_password")
    String confirmPassword;
}
