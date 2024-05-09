package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO для задачи
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    @ApiModelProperty("Идентификатор задачи")
    Long id;
    @ApiModelProperty("Наименование задачи")
    String title;
    @ApiModelProperty("Ссылка на репозиторий в GitLab")
    @JsonProperty("gitlab_repository_url")
    String gitlabRepositoryUrl;
}
