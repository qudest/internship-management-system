package com.ds.ims.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для аккаунта
 */
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    @ApiModelProperty("Идентификатор аккаунта")
    Long id;
    @ApiModelProperty("Логин")
    String username;
}
