package com.ds.ims.api.dto;

import com.ds.ims.storage.entity.status.UserTaskStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для оценивания задачи
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskReviewDto {
    @ApiModelProperty("Статус для задачи")
    UserTaskStatus status;
    @ApiModelProperty("Комментарий к задаче")
    String comment;
}
