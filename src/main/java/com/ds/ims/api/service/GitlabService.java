package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.api.mapper.TaskMapper;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
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

    public Project forkProjectForUser(String projectName, String username) {
        Project project;
        try {
            project = gitLabApi.getProjectApi().getProject("root", projectName);
            return gitLabApi.getProjectApi().forkProject(project.getId(), username);
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while forking project", e);
            //todo сообщение админам
        }
    }

    public User createUser(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getAccount().getUsername());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
        try {
            gitLabApi.getUserApi().createUser(user, userEntity.getAccount().getPassword(), false);
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while creating user", e);
        }
        return user;
    }
}

