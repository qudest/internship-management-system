package com.ds.ims.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для запроса на стажировку
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    @ApiModelProperty("Идентификатор запроса")
    Long id;
    @ApiModelProperty("Пользователь")
    UserDto user;
    @ApiModelProperty("Статус запроса")
    String status;
}
