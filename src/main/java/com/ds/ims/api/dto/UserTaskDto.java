package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTaskDto {
    TaskDto task;
    @JsonProperty("forked_gitlab_repository_url")
    String forkedGitlabRepositoryUrl;
    String status;
}
