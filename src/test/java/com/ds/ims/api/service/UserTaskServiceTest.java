package com.ds.ims.api.service;

import com.ds.ims.api.dto.TaskReviewDto;
import com.ds.ims.storage.entity.*;
import com.ds.ims.storage.entity.status.UserTaskStatus;
import com.ds.ims.storage.repository.UserTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserTaskServiceTest {
    private AdminService adminService;
    private UserTaskRepository userTaskRepository;
    private TaskService taskService;
    private UserService userService;
    private UserTaskService userTaskService;

    @BeforeEach
    void setUp() {
        adminService = Mockito.mock(AdminService.class);
        userTaskRepository = Mockito.mock(UserTaskRepository.class);
        InternshipUserService internshipUserService = Mockito.mock(InternshipUserService.class);
        GitlabService gitlabService = Mockito.mock(GitlabService.class);
        taskService = Mockito.mock(TaskService.class);
        userService = Mockito.mock(UserService.class);
        MessageService messageService = Mockito.mock(MessageService.class);
        userTaskService = new UserTaskService(adminService, userTaskRepository, internshipUserService, gitlabService, taskService, userService, messageService);
    }

    @Test
    void getTaskThrowsNotFoundWhenUserTaskNotFound() {
        Long taskId = 1L;
        Long accountId = 1L;
        when(userService.findByAccountId(accountId)).thenReturn(Optional.of(new UserEntity()));
        when(userTaskRepository.findByUserIdAndTaskId(accountId, taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTaskService.getTask(taskId, accountId));
    }

    @Test
    void forkTaskForInternshipUsersThrowsNotFoundWhenTaskNotFound() {
        Long taskId = 1L;
        when(taskService.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTaskService.forkTaskForInternshipUsers(taskId));
    }

    @Test
    void getUserGradeThrowsNotFoundWhenUserNotFound() {
        Long internshipId = 1L;
        Long accountId = 1L;
        when(userService.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTaskService.getUserGrade(internshipId, accountId));
    }

    @Test
    void reviewTaskThrowsNotFoundWhenUserTaskNotFound() {
        Long userTaskId = 1L;
        Long accountId = 1L;
        TaskReviewDto taskReviewDto = new TaskReviewDto();
        taskReviewDto.setStatus(UserTaskStatus.ACCEPTED);
        when(userTaskRepository.findById(userTaskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTaskService.reviewTask(userTaskId, taskReviewDto, accountId));
    }

    @Test
    void reviewTaskThrowsNotFoundWhenAdminNotFound() {
        Long userTaskId = 1L;
        Long accountId = 1L;
        TaskReviewDto taskReviewDto = new TaskReviewDto();
        taskReviewDto.setStatus(UserTaskStatus.ACCEPTED);
        when(userTaskRepository.findById(userTaskId)).thenReturn(Optional.of(new UserTaskEntity()));
        when(adminService.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTaskService.reviewTask(userTaskId, taskReviewDto, accountId));
    }
}