package com.ds.ims.api.service;

import com.ds.ims.api.dto.CreatingTaskDto;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TaskService {
    GitlabService gitlabService;
    LessonService lessonService;
    TaskRepository taskRepository;

    public ResponseEntity<?> createTask(Long id, Long lessonId, CreatingTaskDto creatingTaskDto) {
        TaskEntity taskEntity = gitlabService.createProject(creatingTaskDto);
        System.out.println(lessonService.findByIdAndInternshipId(lessonId, id));
        taskEntity.setLesson(lessonService.findByIdAndInternshipId(lessonId, id));
        taskRepository.save(taskEntity);
        return ResponseEntity.ok().build();
    }
}
