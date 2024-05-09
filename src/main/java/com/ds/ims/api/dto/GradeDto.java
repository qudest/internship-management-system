package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    String username;
    @JsonProperty("user_tasks")
    List<UserTaskDto> userTasks;
}
