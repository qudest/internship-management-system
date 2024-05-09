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

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для работы с заданиями пользователей
 */
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

    /**
     * Получение всех заданий пользователя по id урока
     *
     * @param lessonId               - id урока
     * @param authenticatedAccountId - id аккаунта
     * @return список заданий пользователя
     */
    public List<UserTaskDto> getTasks(Long lessonId, Long authenticatedAccountId) {
        UserEntity user = userService.findByAccountId(authenticatedAccountId)
                .orElseThrow(() -> new NotFoundException("User with account id " + authenticatedAccountId + " not found"));
        List<UserTaskEntity> userTasks = findAllByUserId(user.getId());
        System.out.println(userTasks.size());
        List<UserTaskDto> userTaskDtos = new ArrayList<>();

        for (UserTaskEntity userTask : userTasks) {
            TaskEntity task = userTask.getTask();
            if (task.getLesson().getId().equals(lessonId)) {
                userTaskDtos.add(UserTaskMapper.INSTANCE.toDto(userTask));
            }
        }

        return userTaskDtos;
    }

    /**
     * Получение задания пользователя по id задания
     *
     * @param taskId                 - id задания
     * @param authenticatedAccountId - id аккаунта
     * @return задание пользователя
     */
    public UserTaskDto getTask(Long taskId, Long authenticatedAccountId) {
        UserEntity user = userService.findByAccountId(authenticatedAccountId)
                .orElseThrow(() -> new NotFoundException("User with account id " + authenticatedAccountId + " not found"));
        UserTaskEntity userTaskEntity = findByUserIdAndTaskId(user.getId(), taskId)
                .orElseThrow(() -> new NotFoundException("User task with task id " + taskId + " not found"));
        return UserTaskMapper.INSTANCE.toDto(userTaskEntity);
    }

    /**
     * Найти задание пользователя по id пользователя и id задания
     *
     * @param userId - id пользователя
     * @param taskId - id задания
     * @return задание пользователя
     */
    public Optional<UserTaskEntity> findByUserIdAndTaskId(Long userId, Long taskId) {
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
    }

    /**
     * Найти задание пользователя по id
     *
     * @param id - id задания
     * @return задание пользователя
     */
    public Optional<UserTaskEntity> findById(Long id) {
        return userTaskRepository.findById(id);
    }

    /**
     * Fork задания для всех активных пользователей стажировки
     *
     * @param taskId - id задания
     * @return статус выполнения
     */
    public ResponseEntity<?> forkTaskForInternshipUsers(Long taskId) {
        TaskEntity task = taskService.findById(taskId).orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        Long internshipId = task.getLesson().getInternship().getId();

        List<UserEntity> users = internshipUserService.findAllByInternshipIdAndStatus(internshipId, InternshipUserStatus.ACTIVE)
                .stream()
                .map(InternshipUserEntity::getUser)
                .collect(Collectors.toList());

        for (UserEntity user : users) {
            Project project = gitlabService.forkProjectForUser(task.getTitle(), user.getAccount().getUsername());
            createUserTask(user, task, project.getHttpUrlToRepo());
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Создание задания пользователя
     *
     * @param user          - пользователь
     * @param task          - задание
     * @param httpUrlToRepo - ссылка на репозиторий
     */
    public void createUserTask(UserEntity user, TaskEntity task, String httpUrlToRepo) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setUser(user);
        userTaskEntity.setTask(task);
        userTaskEntity.setForkedGitlabRepositoryUrl(httpUrlToRepo);
        userTaskEntity.setStatus(UserTaskStatus.IN_PROGRESS);
        userTaskEntity.setLastCheckDate(new Date());
        userTaskRepository.save(userTaskEntity);
    }

    /**
     * Найти все задания пользователя по id пользователя
     *
     * @param userId - id пользователя
     * @return список заданий пользователя
     */
    public List<UserTaskEntity> findAllByUserId(Long userId) {
        return userTaskRepository.findAllByUserId(userId);
    }

    /**
     * Получение успеваемости пользователя по стажировке
     * @param internshipId - id стажировки
     * @param user - пользователь
     * @return успеваемость пользователя
     */
    private GradeDto getGrade(Long internshipId, UserEntity user) {
        List<UserTaskEntity> userTasks = findAllByUserId(user.getId());
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

    /**
     * Получение успеваемости пользователя по стажировке
     * @param internshipId - id стажировки
     * @param authenticatedAccountId - id аккаунта
     * @return успеваемость пользователя
     */
    public GradeDto getUserGrade(Long internshipId, Long authenticatedAccountId) {
        UserEntity user = userService.findByAccountId(authenticatedAccountId)
                .orElseThrow(() -> new NotFoundException("User with account id " + authenticatedAccountId + " not found"));
        return getGrade(internshipId, user);
    }

    /**
     * Получение успеваемости всех пользователей по стажировке
     * @param internshipId - id стажировки
     * @return успеваемость всех пользователей
     */
    public List<GradeDto> getUserGrades(Long internshipId) {
        List<InternshipUserEntity> internshipUsers = internshipUserService.findAllByInternshipIdAndStatus(internshipId, InternshipUserStatus.ACTIVE);
        List<GradeDto> gradeDtos = new ArrayList<>();
        for (InternshipUserEntity internshipUser : internshipUsers) {
            UserEntity user = internshipUser.getUser();
            gradeDtos.add(getGrade(internshipId, user));
        }
        return gradeDtos;
    }

    /**
     * Получение задания пользователя по ссылке на репозиторий
     * @param url - ссылка на репозиторий
     * @return задание пользователя
     */
    public Optional<UserTaskEntity> findByForkedGitlabRepositoryUrl(String url) {
        return userTaskRepository.findByForkedGitlabRepositoryUrl(url);
    }

    /**
     * Получение новых коммитов для стажировки
     * @param internshipId - id стажировки
     * @return список новых коммитов
     */
    public List<CommitDto> getNewCommitsForInternship(Long internshipId) {
        List<CommitDto> commitDtos = new ArrayList<>();
        Map<String, List<Commit>> commits = gitlabService.getCommits();

        for (Map.Entry<String, List<Commit>> entry : commits.entrySet()) {
            UserTaskEntity userTaskEntity = findByForkedGitlabRepositoryUrl(entry.getKey()).orElseThrow(
                    () -> new NotFoundException("User task not found with forked gitlab repository url: " + entry.getKey())
            );
            Long taskInternshipId = userTaskEntity.getTask().getLesson().getInternship().getId();
            if (userTaskEntity.getStatus().equals(UserTaskStatus.ACCEPTED) || !taskInternshipId.equals(internshipId)) {
                continue;
            }
            for (Commit commit : entry.getValue()) {
                commitDtos.add(CommitMapper.INSTANCE.toDto(userTaskEntity, commit));
            }
        }

        return commitDtos;
    }

    /**
     * Проверка коммита
     * @param userTaskId - id задания пользователя
     * @param taskReviewDto - результат проверки
     * @param authenticatedAccountId - id авторизованного аккаунта
     * @return статус выполнения
     */
    public ResponseEntity<?> reviewTask(Long userTaskId, TaskReviewDto taskReviewDto, Long authenticatedAccountId) {
        UserTaskEntity userTaskEntity = findById(userTaskId).orElseThrow(() -> new NotFoundException("User task with id " + userTaskId + " not found"));
        userTaskEntity.setStatus(taskReviewDto.getStatus());

        AdminEntity adminEntity = adminService.findByAccountId(authenticatedAccountId).orElseThrow(
                () -> new NotFoundException("Admin with account id " + authenticatedAccountId + " not found")
        );
        String sender = adminEntity.getName();
        LocalDateTime createdAt = LocalDateTime.now();
        AccountEntity receiver = userTaskEntity.getUser().getAccount();

        MessageDto reviewResult = new MessageDto();
        reviewResult.setText("Task "
                + userTaskEntity.getTask().getTitle()
                + "(" + userTaskEntity.getForkedGitlabRepositoryUrl()
                + ") has been " + taskReviewDto.getStatus().toString().toLowerCase());
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
