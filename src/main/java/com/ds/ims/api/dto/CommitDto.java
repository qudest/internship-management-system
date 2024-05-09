package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO для коммита
 */
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommitDto {
    @JsonProperty("user_task_id")
    Long userTaskId;
    String title;
    @JsonProperty("user_id")
    Long userId;
    String username;
    @JsonProperty("commit_date")
    LocalDateTime commitDate;
    @JsonProperty("last_author")
    String lastAuthor;
    @JsonProperty("web_url")
    String webUrl;
}
