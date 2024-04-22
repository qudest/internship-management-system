package com.ds.ims.api.controller;

import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.ADMIN)
public class AdminController {
    // потом убрать
    private static final String GET_INTERNSHIPS = "/internships"; // POST
    private static final String GET_INTERNSHIP_BY_ID = GET_INTERNSHIPS + "/{id}"; // PUT DELETE
    private static final String GET_LESSONS = GET_INTERNSHIP_BY_ID + "/lessons"; // POST

    private static final String GET_LESSON_BY_ID = GET_LESSONS + "/{id}"; // PUT DELETE
    private static final String GET_TASKS = GET_LESSON_BY_ID + "/tasks"; // POST
    private static final String GET_TASK_BY_ID = GET_TASKS + "/{id}"; // PUT DELETE;

    // Возможность проверить задачи занятия GET

    // Возможность оценить задачу

    // Возможность сформировать ведомость по стажировке




}
