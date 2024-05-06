package com.ds.ims.api.service;

import com.ds.ims.api.dto.*;
import com.ds.ims.api.mapper.CommitMapper;
import com.ds.ims.api.mapper.UserTaskMapper;
import com.ds.ims.storage.entity.*;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.entity.status.UserTaskStatus;
import com.ds.ims.storage.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserTaskService {
    AdminService adminService;
    UserTaskRepository userTaskRepository;
    InternshipUserService internshipUserService;
    GitlabService gitlabService;
    TaskService taskService;
    UserService userService;
    MessageService messageService;

    public List<UserTaskDto> getTasks(Long internshipId, Long lessonId, Long authenticatedAccountId) {
        List<UserTaskEntity> userTasks = userTaskRepository.findAllByUserId(authenticatedAccountId);
        List<UserTaskDto> userTaskDtos = new ArrayList<>();

        for (UserTaskEntity userTask : userTasks) {
            TaskEntity task = userTask.getTask();
            if (task.getLesson().getId().equals(lessonId) && task.getLesson().getInternship().getId().equals(internshipId)) {
                userTaskDtos.add(UserTaskMapper.INSTANCE.toDto(userTask));
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
        return UserTaskMapper.INSTANCE.toDto(userTaskEntity);
    }

    public UserTaskEntity findByUserIdAndTaskId(Long userId, Long taskId) {
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
    }

    public UserTaskEntity findById(Long id) {
        return userTaskRepository.findById(id).orElseThrow(() -> new NullPointerException("User task not found with id: " + id));
    }

    public UserTaskEntity findByForkedGitlabRepositoryUrl(String url) {
        return userTaskRepository.findByForkedGitlabRepositoryUrl(url);
    }

    public ResponseEntity<?> forkTaskForInternshipUsers(Long id, Long lessonId, Long taskId) {
        List<UserEntity> users = internshipUserService.findAllByInternshipIdAndStatus(id, InternshipUserStatus.ACTIVE)
                .stream()
                .map(InternshipUserEntity::getUser)
                .collect(Collectors.toList());
        TaskEntity task = taskService.findById(taskId).get();
        for (UserEntity user : users) {
            Project project = gitlabService.forkProjectForUser(task.getTitle(), user.getAccount().getUsername());
            create(user, task, project.getHttpUrlToRepo());
        }
        return ResponseEntity.ok().build();
    }

    public void create(UserEntity user, TaskEntity task, String httpUrlToRepo) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setUser(user);
        userTaskEntity.setTask(task);
        userTaskEntity.setForkedGitlabRepositoryUrl(httpUrlToRepo);
        userTaskEntity.setStatus(UserTaskStatus.IN_PROGRESS);
        userTaskEntity.setLastCheckDate(new Date());
        userTaskRepository.save(userTaskEntity);
    }

    private GradeDto getGrade(Long internshipId, UserEntity user) {
        List<UserTaskEntity> userTasks = userTaskRepository.findAllByUserId(user.getId());
        List<UserTaskDto> userTaskDtos = new ArrayList<>();
        for (UserTaskEntity userTask : userTasks) {
            if (userTask.getTask().getLesson().getInternship().getId().equals(internshipId)) {
                userTaskDtos.add(UserTaskMapper.INSTANCE.toDto(userTask));
            }
        }
        GradeDto gradeDto = new GradeDto();
        gradeDto.setUsername(user.getAccount().getUsername());
        gradeDto.setUserTasks(userTaskDtos);
        return gradeDto;
    }

    public GradeDto getUserGrade(Long internshipId, Long authenticatedAccountId) {
        UserEntity user = userService.findByAccountId(authenticatedAccountId).orElseThrow(() -> new NullPointerException("User not found with account id: " + authenticatedAccountId));
        return getGrade(internshipId, user);
    }

    public List<GradeDto> getUsersGrade(Long internshipId) {
        List<InternshipUserEntity> internshipUsers = internshipUserService.findAllByInternshipIdAndStatus(internshipId, InternshipUserStatus.ACTIVE);
        List<GradeDto> gradeDtos = new ArrayList<>();
        for (InternshipUserEntity internshipUser : internshipUsers) {
            UserEntity user = internshipUser.getUser();
            gradeDtos.add(getGrade(internshipId, user));
        }
        return gradeDtos;
    }

    public List<CommitDto> getFreshCommits() {
        List<CommitDto> commitDtos = new ArrayList<>();
        Map<String, List<Commit>> commits = gitlabService.getCommits();
        for (Map.Entry<String, List<Commit>> entry : commits.entrySet()) {
            UserTaskEntity userTaskEntity = userTaskRepository.findByForkedGitlabRepositoryUrl(entry.getKey());
            if (userTaskEntity.getStatus() == UserTaskStatus.ACCEPTED) {
                continue;
            }
            for (Commit commit : entry.getValue()) {
                commitDtos.add(CommitMapper.INSTANCE.toDto(userTaskEntity, commit));
            }
        }
        return commitDtos;
    }

    public ResponseEntity<?> reviewTask(Long userTaskId, TaskReviewDto taskReviewDto, Long authenticatedAccountId) {
        UserTaskEntity userTaskEntity = findById(userTaskId);
        userTaskEntity.setStatus(UserTaskStatus.valueOf(taskReviewDto.getStatus()));

        String sender = adminService.findByAccountId(authenticatedAccountId).getName();
        LocalDateTime createdAt = LocalDateTime.now();
        AccountEntity receiver = userTaskEntity.getUser().getAccount();

        MessageDto reviewResult = new MessageDto();
        reviewResult.setText("Task "
                + userTaskEntity.getTask().getTitle()
                + "(" + userTaskEntity.getForkedGitlabRepositoryUrl()
                + ") has been " + taskReviewDto.getStatus().toLowerCase());
        reviewResult.setSenderName(sender);
        reviewResult.setCreatedAt(createdAt);
        messageService.sendMessage(receiver, reviewResult);

        if (!taskReviewDto.getComment().isEmpty()) {
            MessageDto comment = new MessageDto();
            comment.setText(userTaskEntity.getForkedGitlabRepositoryUrl() + " " + taskReviewDto.getComment());
            comment.setSenderName(sender);
            comment.setCreatedAt(createdAt);
            messageService.sendMessage(receiver, comment);
        }

        userTaskRepository.save(userTaskEntity);
        return ResponseEntity.ok().build();
    }
}
