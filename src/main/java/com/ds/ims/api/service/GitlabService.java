package com.ds.ims.api.service;

import com.ds.ims.api.dto.MessageDto;
import com.ds.ims.api.exception.GitlabException;
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

/**
 * Сервис для работы с Gitlab Api
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class GitlabService {
    GitLabApi gitlabApi;
    LastCheckDateService lastCheckDateService;
    MessageService messageService;
    AdminService adminService;

    /**
     * Создание проекта в Gitlab
     *
     * @param title - название проекта
     * @return созданный проект
     */
    public Project createProject(String title) {
        Project project;
        try {
            project = gitlabApi.getProjectApi().createProject(title);
        } catch (GitLabApiException e) {
            MessageDto messageDto = new MessageDto(
                    "Error while creating project with title " + title,
                    "System"
            );
            adminService.findAll().forEach(adminEntity -> messageService.sendMessage(adminEntity.getAccount(), messageDto));

            throw new GitlabException("Error while creating project: " + e.getMessage());
        }
        return project;
    }

    /**
     * Fork проекта для пользователя
     *
     * @param projectName - название проекта
     * @param username    - имя пользователя
     * @return созданный проект
     */
    public Project forkProjectForUser(String projectName, String username) {
        Project project;
        try {
            project = gitlabApi.getProjectApi().getProject("root", projectName);
            return gitlabApi.getProjectApi().forkProject(project.getId(), username);
        } catch (GitLabApiException e) {
            MessageDto messageDto = new MessageDto(
                    "Error while forking project with project name " + projectName + " for user with username " + username,
                    "System"
            );
            adminService.findAll().forEach(adminEntity -> messageService.sendMessage(adminEntity.getAccount(), messageDto));

            throw new GitlabException("Error while forking project: " + e.getMessage());
        }
    }

    /**
     * Создание пользователя в Gitlab
     *
     * @param userEntity - пользователь
     */
    public void createUser(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getAccount().getUsername());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
        try {
            gitlabApi.getUserApi().createUser(user, userEntity.getAccount().getUsername(), true);
        } catch (GitLabApiException e) {
            MessageDto messageDto = new MessageDto(
                    "Error while creating user with username " + userEntity.getAccount().getUsername(),
                    "System"
            );
            adminService.findAll().forEach(adminEntity -> messageService.sendMessage(adminEntity.getAccount(), messageDto));

            throw new GitlabException("Error while creating user: " + e.getMessage());
        }
    }

    /**
     * Получение всех коммитов
     *
     * @return все коммиты
     */
    public Map<String, List<Commit>> getCommits() {
        Map<String, List<Commit>> allCommits = new HashMap<>();
        try {
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
        } catch (GitLabApiException e) {
            throw new GitlabException("Error while getting commits: " + e.getMessage());
        }
        return allCommits;
    }
}

