package com.ds.ims.api.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommitDto {
    Long userTaskId; // ???
    String title;
    Long userId;
    String username;
    LocalDateTime commitDate;
    String lastAuthor;
    String webUrl;
}
