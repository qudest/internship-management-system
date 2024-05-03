package com.ds.ims.api.utils;

public class ApiPaths {
    public static final String BASE_API = "/api";

    public static final String PUBLIC = BASE_API + "/public";
    public static final String USER = BASE_API + "/user";
    public static final String ADMIN = BASE_API + "/admin";

    public static final String INTERNSHIPS = "/internships";
    public static final String INTERNSHIP_BY_ID = INTERNSHIPS + "/{id}";
    public static final String LESSONS = INTERNSHIP_BY_ID + "/lessons";
    public static final String LESSON_BY_ID = LESSONS + "/{lessonId}";
    public static final String TASKS = LESSON_BY_ID + "/tasks";
    public static final String TASK_BY_ID = TASKS + "/{taskId}";

    public static final String REQUESTS = INTERNSHIP_BY_ID + "/requests";
    public static final String REQUEST_BY_ID = REQUESTS + "/{requestId}";

    public static final String GRADE = INTERNSHIP_BY_ID + "/grade";
}
