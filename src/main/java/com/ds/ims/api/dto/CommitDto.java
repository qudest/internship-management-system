package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO для коммита
 */
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommitDto {
    @ApiModelProperty("Идентификатор задания пользователя")
    @JsonProperty("user_task_id")
    Long userTaskId;
    @ApiModelProperty("Наименование задания")
    String title;
    @ApiModelProperty("Идентификатор пользователя")
    @JsonProperty("user_id")
    Long userId;
    @ApiModelProperty("Логин пользователя")
    String username;
    @ApiModelProperty("Дата коммита")
    @JsonProperty("commit_date")
    LocalDateTime commitDate;
    @ApiModelProperty("Последний автор")
    @JsonProperty("last_author")
    String lastAuthor;
    @ApiModelProperty("Ссылка на коммит")
    @JsonProperty("web_url")
    String webUrl;
}
