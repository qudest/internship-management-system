package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.api.mapper.TaskMapper;
import com.ds.ims.storage.entity.LessonEntity;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.models.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с заданиями
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskService {
    GitlabService gitlabService;
    LessonService lessonService;
    TaskRepository taskRepository;

    /**
     * Создание задания
     *
     * @param lessonId        - id урока
     * @param creatingTaskDto - данные для создания задания
     * @return созданное задание
     */
    public ResponseEntity<?> createTask(Long lessonId, CreatingTaskDto creatingTaskDto) {
        Project project = gitlabService.createProject(creatingTaskDto.getTitle());
        TaskEntity taskEntity = TaskMapper.INSTANCE.gitlabProjectToTaskEntity(project);
        LessonEntity lessonEntity = lessonService.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson with id " + lessonId + " not found"));
        taskEntity.setLesson(lessonEntity);
        taskRepository.save(taskEntity);
        return ResponseEntity.ok(TaskMapper.INSTANCE.toDto(taskEntity));
    }

    /**
     * Найти задание по id
     *
     * @param id - id задания
     * @return задание
     */
    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.findById(id);
    }
}
