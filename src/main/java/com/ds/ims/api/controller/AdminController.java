package com.ds.ims.api.controller;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.service.InternshipService;
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
    // потом убрать
    // СДЕЛАНО GET_INTERNSHIPS = "/internships"; // POST
    // GET_INTERNSHIP_BY_ID = GET_INTERNSHIPS + "/{id}"; // PUT DELETE
    // GET_LESSONS = GET_INTERNSHIP_BY_ID + "/lessons"; // POST

    // GET_LESSON_BY_ID = GET_LESSONS + "/{id}"; // PUT DELETE
    // GET_TASKS = GET_LESSON_BY_ID + "/tasks"; // POST
    // GET_TASK_BY_ID = GET_TASKS + "/{id}"; // PUT DELETE;

    // Возможность проверить задачи занятия GET

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

    @DeleteMapping(ApiPaths.INTERNSHIP_BY_ID)
    public ResponseEntity<?> deleteInternship(@PathVariable Long id) {
        return internshipService.deleteInternship(id);
    }
}
