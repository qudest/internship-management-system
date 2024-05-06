package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.api.mapper.TaskMapper;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class GitlabService {
    GitLabApi gitlabApi;
    LastCheckDateService lastCheckDateService;

    public TaskEntity createProject(CreatingTaskDto creatingTaskDto) {
        Project project;
        try {
            project = gitlabApi.getProjectApi().createProject(creatingTaskDto.getTitle());
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while creating project", e);
        }
        return TaskMapper.INSTANCE.gitlabProjectToTaskEntity(project);
    }

    public void deleteProject(String projectName) {
        Project project;
        try {
            project = gitlabApi.getProjectApi().getProject("root", projectName);
            gitlabApi.getProjectApi().deleteProject(project);
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while deleting project", e);
        }
    }

    public Project forkProjectForUser(String projectName, String username) {
        Project project;
        try {
            project = gitlabApi.getProjectApi().getProject("root", projectName);
            return gitlabApi.getProjectApi().forkProject(project.getId(), username);
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
            //todo пароль расшифровать
            gitlabApi.getUserApi().createUser(user, userEntity.getAccount().getPassword(), false);
        } catch (GitLabApiException e) {
            throw new RuntimeException("Error while creating user", e);
        }
        return user;
    }

    public Map<String, List<Commit>> getCommits() {
        try {
            Map<String, List<Commit>> allCommits = new HashMap<>();
            List<Project> projects = gitlabApi.getProjectApi().getProjects();
            for (Project project : projects) {
                if (project.getOwner().getUsername().equals("root")) {
                    continue;
                }
                Date since = lastCheckDateService.getLastCheckDateByUrl(project.getHttpUrlToRepo());
                List<Commit> commits = gitlabApi.getCommitsApi().getCommits(project.getId(), null, since, null);
                allCommits.put(project.getHttpUrlToRepo(), commits);
                lastCheckDateService.updateLastCheckDateByUrl(project.getHttpUrlToRepo(), new Date());
            }
            return allCommits;
        } catch (GitLabApiException e) {
            throw new RuntimeException(e);
        }
    }
}

