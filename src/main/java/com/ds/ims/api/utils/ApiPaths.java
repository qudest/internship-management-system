package com.ds.ims.api.utils;

public class ApiPaths {
    public static final String BASE_API = "/api";

    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";

    public static final String PUBLIC = BASE_API + "/public";
    public static final String USER = BASE_API + "/user";
    public static final String ADMIN = BASE_API + "/admin";

    public static final String INTERNSHIPS = "/internships";
    public static final String INTERNSHIP_BY_ID = INTERNSHIPS + "/{internshipId}";
    public static final String LESSONS = "/lessons";
    public static final String LESSON_BY_ID = LESSONS + "/{lessonId}";
    public static final String TASKS = "/tasks";
    public static final String TASK_BY_ID = TASKS + "/{taskId}";

    public static final String REQUESTS = "/requests";
    public static final String REQUEST_BY_ID = REQUESTS + "/{requestId}";

    public static final String GRADES = INTERNSHIP_BY_ID + "/grades";

    public static final String MESSAGES = "/messages";

    public static final String NEW_COMMITS = INTERNSHIP_BY_ID + "/new-commits";
    public static final String REVIEW = "/user-tasks/{userTaskId}";

    public static final String INTERNSHIP_USERS = INTERNSHIP_BY_ID + "/users";
    public static final String INTERNSHIP_USER_BY_ID = INTERNSHIP_USERS + "/{userId}";
}
