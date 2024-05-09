package com.ds.ims.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для урока
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDto {
    Long id;
    String title;
    String content;
}
