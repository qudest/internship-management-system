package com.ds.ims.api.controller;

import com.ds.ims.api.dto.*;
import com.ds.ims.api.service.*;
import com.ds.ims.api.utils.ApiPaths;
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

    /**
     * Создание стажировки
     * POST /api/admin/internships
     *
     * @param internshipDto - данные для создания стажировки
     * @return - ответ с результатом создания стажировки
     */
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
    @PutMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> updateInternship(@PathVariable Long internshipId, @RequestBody InternshipDto internshipDto) {
        return internshipService.updateInternship(internshipId, internshipDto);
    }

    /**
     * Удаление стажировки
     * DELETE /api/admin/internships/{id}
     *
     * @param internshipId - id стажировки
     * @return - ответ с результатом удаления стажировки
     */
    @DeleteMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> deleteInternship(@PathVariable Long internshipId) {
        return internshipService.deleteInternship(internshipId);
    }

    /**
     * Создание урока
     * POST /api/admin/internships/{id}/lessons
     *
     * @param internshipId - id стажировки
     * @param lessonDto    - данные для создания урока
     * @return - ответ с результатом создания урока
     */
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
    @PutMapping(ApiPaths.LESSON_BY_ID)
    public ResponseEntity<?> updateLesson(@PathVariable Long lessonId, @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(lessonId, lessonDto);
    }

    /**
     * Удаление урока
     * DELETE /api/admin/lessons/{id}
     *
     * @param lessonId - id урока
     * @return - ответ с результатом удаления урока
     */
    @DeleteMapping(ApiPaths.LESSON_BY_ID)
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }

    /**
     * Создание задания
     * POST /api/admin/lessons/{id}/tasks
     *
     * @param lessonId        - id урока
     * @param creatingTaskDto - данные для создания задания
     * @return - ответ с результатом создания задания
     */
    @PostMapping(ApiPaths.LESSON_BY_ID + ApiPaths.TASKS)
    public ResponseEntity<?> createTask(@PathVariable Long lessonId, @RequestBody CreatingTaskDto creatingTaskDto) {
        return taskService.createTask(lessonId, creatingTaskDto);
    }

    /**
     * Fork задания для стажеров
     * POST /api/admin/tasks/{taskId}
     *
     * @param taskId - id задания
     * @return - ответ с результатом fork задания для стажеров
     */
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
    @PutMapping(ApiPaths.REVIEW)
    public ResponseEntity<?> reviewTask(@PathVariable Long userTaskId, @RequestBody TaskReviewDto taskReviewDto) {
        return userTaskService.reviewTask(userTaskId, taskReviewDto, authService.getAuthenticatedAccountId());
    }
}
