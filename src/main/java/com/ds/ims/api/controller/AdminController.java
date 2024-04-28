package com.ds.ims.api.controller;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.api.service.InternshipService;
import com.ds.ims.api.service.LessonService;
import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.ADMIN)
public class AdminController {
    InternshipService internshipService;
    LessonService lessonService;
    // потом убрать
    // СДЕЛАНО GET_INTERNSHIPS = "/internships"; // POST
    // СДЕЛАНО GET_INTERNSHIP_BY_ID = GET_INTERNSHIPS + "/{id}"; // PUT DELETE
    // СДЕЛАНО GET_LESSONS = GET_INTERNSHIP_BY_ID + "/lessons"; // POST

    // СДЕЛАНО GET_LESSON_BY_ID = GET_LESSONS + "/{id}"; // PUT DELETE
    // GET_TASKS = GET_LESSON_BY_ID + "/tasks"; // POST
    // GET_TASK_BY_ID = GET_TASKS + "/{id}"; // PUT DELETE;

    // Возможность проверить задачи занятия GET?

    // Возможность оценить задачу

    // Возможность сформировать ведомость по стажировке

    @PostMapping(ApiPaths.INTERNSHIPS)
    public ResponseEntity<?> createInternship(@RequestBody InternshipDto internshipDto) {
        return internshipService.createInternship(internshipDto);
    }

    @PutMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> updateInternship(@PathVariable Long id, @RequestBody InternshipDto internshipDto) {
        return internshipService.updateInternship(id, internshipDto);
    }

    //todo сделать удаление юзеров от стажировки, уроков, тасков
    @DeleteMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> deleteInternship(@PathVariable Long id) {
        return internshipService.deleteInternship(id);
    }

    @PostMapping(ApiPaths.LESSONS)
    public ResponseEntity<?> createLesson(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        return lessonService.createLesson(id, lessonDto);
    }

    @PutMapping(ApiPaths.LESSON_BY_ID)
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @PathVariable Long lessonId, @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(id, lessonId, lessonDto);
    }

    @DeleteMapping(ApiPaths.LESSON_BY_ID)
    public ResponseEntity<?> deleteLesson(@PathVariable Long id, @PathVariable Long lessonId) {
        return lessonService.deleteLesson(id, lessonId);
    }
}
