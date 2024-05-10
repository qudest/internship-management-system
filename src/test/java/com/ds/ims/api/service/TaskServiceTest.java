package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.storage.entity.LessonEntity;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.repository.TaskRepository;
import org.gitlab4j.api.models.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    private GitlabService gitlabService;
    private LessonService lessonService;
    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        gitlabService = Mockito.mock(GitlabService.class);
        lessonService = Mockito.mock(LessonService.class);
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(gitlabService, lessonService, taskRepository);
    }

    @Test
    void createTaskReturnsCreatedTask() {
        Long lessonId = 1L;
        CreatingTaskDto creatingTaskDto = new CreatingTaskDto();
        when(lessonService.findById(lessonId)).thenReturn(Optional.of(new LessonEntity()));
        when(gitlabService.createProject(creatingTaskDto.getTitle())).thenReturn(new Project());
        when(taskRepository.save(Mockito.any(TaskEntity.class))).thenReturn(new TaskEntity());

        ResponseEntity<?> result = taskService.createTask(lessonId, creatingTaskDto);
        assertNotNull(result.getBody());
    }

    @Test
    void createTaskThrowsNotFoundWhenLessonNotFound() {
        Long lessonId = 1L;
        CreatingTaskDto creatingTaskDto = new CreatingTaskDto();
        when(lessonService.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.createTask(lessonId, creatingTaskDto));
    }

    @Test
    void findByIdReturnsTaskEntity() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new TaskEntity()));

        Optional<TaskEntity> result = taskService.findById(taskId);
        assertTrue(result.isPresent());
    }

    @Test
    void findByIdReturnsEmptyWhenTaskNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Optional<TaskEntity> result = taskService.findById(taskId);
        assertFalse(result.isPresent());
    }

}