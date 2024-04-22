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
@RequestMapping(ApiPaths.USER)
public class UserController {
    // потом убрать
    private static final String GET_INTERNSHIPS = "/internships"; // GET
    private static final String GET_INTERNSHIP_BY_ID = GET_INTERNSHIPS + "/{id}"; // GET POST DELETE
    private static final String GET_LESSONS = GET_INTERNSHIP_BY_ID + "/lessons"; // GET
    private static final String GET_LESSON_BY_ID = GET_LESSONS + "/{id}"; // GET
    private static final String GET_TASKS = GET_LESSON_BY_ID + "/tasks"; // GET
    private static final String GET_TASK_BY_ID = GET_TASKS + "/{id}"; // GET

    // Возможность посмотреть успеваемость

    // Возможность получать все сообщения пользователя
}
