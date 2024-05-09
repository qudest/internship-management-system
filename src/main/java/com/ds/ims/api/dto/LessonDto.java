package com.ds.ims.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для урока
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDto {
    @ApiModelProperty("Идентификатор урока")
    Long id;
    @ApiModelProperty("Наименование урока")
    String title;
    @ApiModelProperty("Содержание урока")
    String content;
}
