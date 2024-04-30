package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskService {
    GitlabService gitlabService;
    LessonService lessonService;
    TaskRepository taskRepository;

    public ResponseEntity<?> createTask(Long id, Long lessonId, CreatingTaskDto creatingTaskDto) {
        TaskEntity taskEntity = gitlabService.createProject(creatingTaskDto);
        taskEntity.setLesson(lessonService.findByIdAndInternshipId(lessonId, id));
        taskRepository.save(taskEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteTask(Long id, Long lessonId, Long taskId) {
        gitlabService.deleteProject(findById(taskId).get().getTitle());
        taskRepository.deleteById(taskId);
        return ResponseEntity.ok().build();
    }

    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.findById(id);
    }

    //todo impl
    public ResponseEntity<?> forkTask(Long id, Long lessonId, Long taskId) {

        return ResponseEntity.ok().build();
    }

    public List<TaskEntity> findAllByLessonId(Long lessonId) {
        return taskRepository.findAllByLessonId(lessonId);
    }
}
