package com.ds.ims.api.controller;

import com.ds.ims.api.dto.*;
import com.ds.ims.api.service.*;
import com.ds.ims.api.utils.ApiPaths;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для администратора
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.ADMIN)
public class AdminController {
    AuthService authService;
    InternshipService internshipService;
    LessonService lessonService;
    TaskService taskService;
    InternshipRequestService internshipRequestService;
    UserTaskService userTaskService;
    MessageService messageService;
    InternshipUserService internshipUserService;

    /**
     * Создание стажировки
     * POST /api/admin/internships
     *
     * @param internshipDto - данные для создания стажировки
     * @return - ответ с результатом создания стажировки
     */
    @ApiOperation(value = "Создание стажировки")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping(ApiPaths.INTERNSHIPS)
    public ResponseEntity<?> createInternship(@RequestBody InternshipDto internshipDto) {
        return internshipService.createInternship(internshipDto);
    }

    /**
     * Обновление стажировки
     * PUT /api/admin/internships/{id}
     *
     * @param internshipId  - id стажировки
     * @param internshipDto - данные для обновления стажировки
     * @return - ответ с результатом обновления стажировки
     */
    @ApiOperation(value = "Обновление стажировки")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> updateInternship(@PathVariable Long internshipId, @RequestBody InternshipDto internshipDto) {
        return internshipService.updateInternship(internshipId, internshipDto);
    }

    /**
     * Создание урока
     * POST /api/admin/internships/{id}/lessons
     *
     * @param internshipId - id стажировки
     * @param lessonDto    - данные для создания урока
     * @return - ответ с результатом создания урока
     */
    @ApiOperation(value = "Создание урока")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping(ApiPaths.INTERNSHIP_BY_ID + ApiPaths.LESSONS)
    public ResponseEntity<?> createLesson(@PathVariable Long internshipId, @RequestBody LessonDto lessonDto) {
        return lessonService.createLesson(internshipId, lessonDto);
    }

    /**
     * Обновление урока
     * PUT /api/admin/lessons/{id}
     *
     * @param lessonId  - id урока
     * @param lessonDto - данные для обновления урока
     * @return - ответ с результатом обновления урока
     */
    @ApiOperation(value = "Обновление урока")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping(ApiPaths.LESSON_BY_ID)
    public ResponseEntity<?> updateLesson(@PathVariable Long lessonId, @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(lessonId, lessonDto);
    }

    /**
     * Создание задания
     * POST /api/admin/lessons/{id}/tasks
     *
     * @param lessonId        - id урока
     * @param creatingTaskDto - данные для создания задания
     * @return - ответ с результатом создания задания
     */
    @ApiOperation(value = "Создание задания")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping(ApiPaths.LESSON_BY_ID + ApiPaths.TASKS)
    public ResponseEntity<?> createTask(@PathVariable Long lessonId, @RequestBody CreatingTaskDto creatingTaskDto) {
        return taskService.createTask(lessonId, creatingTaskDto);
    }

    /**
     * Fork задания для пользователей
     * POST /api/admin/tasks/{taskId}
     *
     * @param taskId - id задания
     * @return - ответ с результатом fork задания для пользователей
     */
    @ApiOperation(value = "Fork задания для пользователей")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping(ApiPaths.TASK_BY_ID)
    public ResponseEntity<?> forkTaskForInternshipUsers(@PathVariable Long taskId) {
        return userTaskService.forkTaskForInternshipUsers(taskId);
    }

    /**
     * Получение ожидающих запросов на стажировку
     * GET /api/admin/internships/{internshipId}/requests
     *
     * @param internshipId - id стажировки
     * @return - список запросов на стажировку
     */
    @ApiOperation(value = "Получение ожидающих запросов на стажировку")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping(ApiPaths.INTERNSHIP_BY_ID + ApiPaths.REQUESTS)
    public List<RequestDto> getPendingRequestsForInternship(@PathVariable Long internshipId) {
        return internshipRequestService.getPendingInternshipRequests(internshipId);
    }

    /**
     * Рассмотрение запроса на стажировку
     * PUT /api/admin/requests/{requestId}
     *
     * @param requestId  - id запроса
     * @param isAccepted - принят ли запрос
     * @return - ответ с результатом рассмотрения запроса
     */
    @ApiOperation(value = "Рассмотрение запроса на стажировку")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping(ApiPaths.REQUEST_BY_ID)
    public ResponseEntity<?> considerRequest(@PathVariable Long requestId, @RequestParam boolean isAccepted) {
        return internshipRequestService.considerRequest(requestId, isAccepted);
    }

    /**
     * Получение ведомости по стажировке
     * GET /api/admin/internships/{internshipId}/grades
     *
     * @param internshipId - id стажировки
     * @return - ведомость по стажировке
     */
    @ApiOperation(value = "Получение ведомости по стажировке")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")                                                // goida
    })
    @GetMapping(ApiPaths.GRADES)
    public List<GradeDto> getGrades(@PathVariable Long internshipId) {
        return userTaskService.getUserGrades(internshipId);
    }

    /**
     * Получение новых коммитов для стажировки
     * GET /api/admin/internships/{internshipId}/new-commits
     *
     * @param internshipId - id стажировки
     * @return - список новых коммитов
     */
    @ApiOperation(value = "Получение новых коммитов для стажировки")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping(ApiPaths.NEW_COMMITS)
    public List<CommitDto> getNewCommits(@PathVariable Long internshipId) {
        return userTaskService.getNewCommitsForInternship(internshipId);
    }

    /**
     * Получение сообщений
     * GET /api/admin/messages
     *
     * @return - список сообщений
     */
    @ApiOperation(value = "Получение сообщений")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping(ApiPaths.MESSAGES)
    public List<MessageDto> getMessages() {
        return messageService.getMessages(authService.getAuthenticatedAccountId());
    }

    /**
     * Проверика задания
     * PUT /api/admin/user-tasks/{userTaskId}
     *
     * @param userTaskId    - id задания пользователя
     * @param taskReviewDto - данные для проверки задания
     * @return - ответ с результатом проверки задания
     */
    @ApiOperation(value = "Проверка задания пользователя")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping(ApiPaths.REVIEW)
    public ResponseEntity<?> reviewTask(@PathVariable Long userTaskId, @RequestBody TaskReviewDto taskReviewDto) {
        return userTaskService.reviewTask(userTaskId, taskReviewDto, authService.getAuthenticatedAccountId());
    }

    /**
     * Обновление статуса пользователя стажировки
     * PUT /api/admin/internships/{internshipId}/users/{userId}
     *
     * @param internshipId - id стажировки
     * @param userId - id пользователя
     * @param status - статус
     * @return - ответ с результатом обновления статуса пользователя стажировки
     */
    @ApiOperation(value = "Обновление статуса пользователя стажировки")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping(ApiPaths.INTERNSHIP_USER_BY_ID)
    public ResponseEntity<?> updateInternshipUserStatus(@PathVariable Long internshipId, @PathVariable Long userId, @RequestParam InternshipUserStatus status) {
        return internshipUserService.updateStatus(internshipId, userId, status);
    }
}
