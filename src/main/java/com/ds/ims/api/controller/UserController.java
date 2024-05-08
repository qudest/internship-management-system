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

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.USER)
public class UserController {
    InternshipUserService internshipUserService;
    InternshipRequestService internshipRequestService;
    LessonService lessonService;
    UserTaskService userTaskService;
    AuthService authService;
    MessageService messageService;

    /**
     * Получение списка стажировок, на которые зарегистрирован пользователь
     * GET api/user/internships
     *
     * @return - список стажировок
     */
    @GetMapping(ApiPaths.INTERNSHIPS)
    public List<InternshipDto> getInternshipsWithRegistration() {
        return internshipUserService.getInternshipsForUser(authService.getAuthenticatedAccountId());
    }

    /**
     * Регистрация на стажировку
     * PUT api/user/internships/{id}
     *
     * @param internshipId - id стажировки
     * @param userDto      - данные пользователя
     * @return - статус регистрации
     */
    @PutMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> registerToInternship(@PathVariable Long internshipId, @RequestBody UserDto userDto) {
        return internshipRequestService.registerToInternship(internshipId, authService.getAuthenticatedAccountId(), userDto);
    }

    /**
     * Отмена регистрации на стажировку
     * DELETE api/user/internships/{id}
     *
     * @param internshipId - id стажировки
     * @return - статус отмены регистрации
     */
    @DeleteMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> deleteRequestToInternship(@PathVariable Long internshipId) {
        return internshipRequestService.deleteRequestToInternship(internshipId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение списка уроков по стажировке
     * GET api/user/internships/{internshipId}/lessons
     *
     * @param internshipId - id стажировки
     * @return - список уроков
     */
    @GetMapping(ApiPaths.INTERNSHIP_BY_ID + ApiPaths.LESSONS)
    public List<LessonDto> getLessons(@PathVariable Long internshipId) {
        return lessonService.getLessons(internshipId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение урока по id
     * GET api/user/lessons/{lessonId}
     *
     * @param lessonId - id урока
     * @return - урок
     */
    @GetMapping(ApiPaths.LESSON_BY_ID)
    public LessonDto getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение списка заданий по уроку
     * GET api/user/lessons/{lessonId}/tasks
     *
     * @param lessonId - id урока
     * @return - список заданий
     */
    @GetMapping(ApiPaths.LESSON_BY_ID + ApiPaths.TASKS)
    public List<UserTaskDto> getTasks(@PathVariable Long lessonId) {
        return userTaskService.getTasks(lessonId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение задания по id
     * GET api/user/tasks/{taskId}
     *
     * @param taskId - id задания
     * @return - задание
     */
    @GetMapping(ApiPaths.TASK_BY_ID)
    public UserTaskDto getTask(@PathVariable Long taskId) {
        return userTaskService.getTask(taskId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение оценок пользователя по стажировке
     * GET api/user/internships/{internshipId}/grades
     *
     * @param internshipId - id стажировки
     * @return - оценки
     */
    @GetMapping(ApiPaths.GRADES)
    public GradeDto getGrades(@PathVariable Long internshipId) {
        return userTaskService.getUserGrade(internshipId, authService.getAuthenticatedAccountId());
    }

    /**
     * Получение сообщений
     * GET api/user/messages
     *
     * @return - список сообщений
     */
    @GetMapping("/messages")
    public List<MessageDto> getMessages() {
        return messageService.getMessages(authService.getAuthenticatedAccountId());
    }
}
