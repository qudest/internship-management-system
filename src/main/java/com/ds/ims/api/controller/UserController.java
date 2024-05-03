package com.ds.ims.api.controller;

import com.ds.ims.api.dto.*;
import com.ds.ims.api.service.AuthService;
import com.ds.ims.api.service.InternshipService;
import com.ds.ims.api.service.LessonService;
import com.ds.ims.api.service.UserTaskService;
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
    InternshipService internshipService;
    LessonService lessonService;
    UserTaskService userTaskService;
    AuthService authService;
    // потом убрать
    // СДЕЛАНО GET_INTERNSHIPS // GET
    // СДЕЛАНО GET_INTERNSHIP_BY_ID // PUT DELETE
    // СДЕЛАНО GET_LESSONS = GET_INTERNSHIP_BY_ID + "/lessons"; // GET
    // СДЕЛАНО GET_LESSON_BY_ID = GET_LESSONS + "/{id}"; // GET
    // GET_TASKS = GET_LESSON_BY_ID + "/tasks"; // GET
    // GET_TASK_BY_ID = GET_TASKS + "/{id}"; // GET
    // Возможность посмотреть успеваемость
    // Возможность получать все сообщения пользователя

    @GetMapping(ApiPaths.INTERNSHIPS)
    public List<InternshipDto> getInternshipsWithRegistration() {
        return internshipService.getInternshipsForUser(authService.getAuthenticatedAccountId());
    }

    @PutMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> registerToInternship(@PathVariable Long id, @RequestBody UserDto userDto) {
        return internshipService.registerToInternship(id, authService.getAuthenticatedAccountId(), userDto);
    }

    @DeleteMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> deleteRequestToInternship(@PathVariable Long id) {
        return internshipService.deleteRequestToInternship(id, authService.getAuthenticatedAccountId());
    }

    @GetMapping(ApiPaths.LESSONS)
    public List<LessonDto> getLessons(@PathVariable Long id) {
        return lessonService.getLessons(id, authService.getAuthenticatedAccountId());
    }

    @GetMapping(ApiPaths.LESSON_BY_ID)
    public LessonDto getLesson(@PathVariable Long id, @PathVariable Long lessonId) {
        return lessonService.getLesson(id, lessonId, authService.getAuthenticatedAccountId());
    }

    @GetMapping(ApiPaths.TASKS)
    public List<UserTaskDto> getTasks(@PathVariable Long id, @PathVariable Long lessonId) {
        return userTaskService.getTasks(id, lessonId, authService.getAuthenticatedAccountId());
    }

    @GetMapping(ApiPaths.TASK_BY_ID)
    public UserTaskDto getTask(@PathVariable Long id, @PathVariable Long lessonId, @PathVariable Long taskId) {
        return userTaskService.getTask(id, lessonId, taskId, authService.getAuthenticatedAccountId());
    }

    @GetMapping(ApiPaths.GRADE)
    public GradeDto getGrade(@PathVariable Long id) {
        return userTaskService.getUserGrade(id, authService.getAuthenticatedAccountId());
    }
}
