package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.api.mapper.TaskMapper;
import com.ds.ims.storage.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class GitlabService {
    GitLabApi gitLabApi;

    public TaskEntity createProject(CreatingTaskDto creatingTaskDto) {
        Project project;
        try {
            project = gitLabApi.getProjectApi().createProject(creatingTaskDto.getTitle());
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while creating project", e);
        }
        return TaskMapper.INSTANCE.gitlabProjectToTaskEntity(project);
    }

    public void deleteProject(String projectName) {
        Project project;
        try {
            project = gitLabApi.getProjectApi().getProject("root", projectName);
            gitLabApi.getProjectApi().deleteProject(project);
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while deleting project", e);
        }
    }
}
