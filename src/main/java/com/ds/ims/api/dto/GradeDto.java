package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO для успеваемости
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeDto {
    @ApiModelProperty("Логин пользователя")
    String username;
    @ApiModelProperty("Задачи пользователя")
    @JsonProperty("user_tasks")
    List<UserTaskDto> userTasks;
}
