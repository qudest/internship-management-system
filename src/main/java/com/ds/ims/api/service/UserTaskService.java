package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserTaskDto;
import com.ds.ims.api.mapper.UserTaskMapper;
import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.UserTaskEntity;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.entity.status.UserTaskStatus;
import com.ds.ims.storage.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.models.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserTaskService {
    UserTaskRepository userTaskRepository;
    InternshipUserService internshipUserService;
    GitlabService gitlabService;
    TaskService taskService;
    UserService userService;

    public List<UserTaskDto> getTasks(Long internshipId, Long lessonId, Long authenticatedAccountId) {
        List<UserTaskEntity> userTasks = userTaskRepository.findAllByUserId(authenticatedAccountId);
        List<UserTaskDto> userTaskDtos = new ArrayList<>();

        for (UserTaskEntity userTask : userTasks) {
            TaskEntity task = userTask.getTask();
            if (task.getLesson().getId().equals(lessonId) && task.getLesson().getInternship().getId().equals(internshipId)) {
                userTaskDtos.add(UserTaskMapper.INSTANCE.toDto(task.getTitle(), userTask));
            }
        }

        return userTaskDtos;
    }

    public UserTaskDto getTask(Long internshipId, Long lessonId, Long taskId, Long authenticatedAccountId) {
        TaskEntity task = taskService.findById(taskId).orElseThrow(() -> new NullPointerException("Task not found with id: " + taskId));

        if (!task.getLesson().getId().equals(lessonId) || !task.getLesson().getInternship().getId().equals(internshipId)) {
            throw new BadRequestException("Task does not belong to the specified lesson and internship");
        }

        UserEntity user = userService.findByAccountId(authenticatedAccountId).orElseThrow(() -> new NullPointerException("User not found with account id: " + authenticatedAccountId));

        UserTaskEntity userTaskEntity = findByUserIdAndTaskId(user.getId(), taskId);
        return UserTaskMapper.INSTANCE.toDto(task.getTitle(), userTaskEntity);
    }

    public UserTaskEntity findByUserIdAndTaskId(Long userId, Long taskId) {
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
    }

    public ResponseEntity<?> forkTaskForInternshipUsers(Long id, Long lessonId, Long taskId) {
        List<UserEntity> users = internshipUserService.findAllByInternshipIdAndStatus(id, InternshipUserStatus.ACTIVE)
                .stream()
                .map(InternshipUserEntity::getUser)
                .collect(Collectors.toList());
        TaskEntity task = taskService.findById(taskId).get();
        for (UserEntity user: users) {
            Project project = gitlabService.forkProjectForUser(task.getTitle(), user.getAccount().getUsername());
            create(user, task, project.getHttpUrlToRepo());
        }
        return ResponseEntity.ok().build();
    }

    public void create(UserEntity user, TaskEntity task, String httpUrlToRepo) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setUser(user);
        userTaskEntity.setTask(task);
        userTaskEntity.setForkedGitLabRepositoryUrl(httpUrlToRepo);
        userTaskEntity.setStatus(UserTaskStatus.IN_PROGRESS);
        userTaskRepository.save(userTaskEntity);
    }
}
