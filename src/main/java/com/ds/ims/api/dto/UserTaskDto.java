package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для задачи пользователя
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTaskDto {
    @ApiModelProperty("Задача")
    TaskDto task;
    @ApiModelProperty("Ссылка на форкнутый репозиторий в GitLab")
    @JsonProperty("forked_gitlab_repository_url")
    String forkedGitlabRepositoryUrl;
    @ApiModelProperty("Статус задачи")
    String status;
}
