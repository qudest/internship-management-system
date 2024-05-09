package com.ds.ims.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для создания задачи
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatingTaskDto {
    @ApiModelProperty("Наименование задачи")
    String title;
}
