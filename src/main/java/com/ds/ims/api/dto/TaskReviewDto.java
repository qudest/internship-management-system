package com.ds.ims.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для оценивания задачи
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskReviewDto {
    String status;
    String comment;
}
