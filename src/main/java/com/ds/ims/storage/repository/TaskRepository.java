package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findById(Long id);
    void deleteById(Long id);
    List<TaskEntity> findAllByLessonId(Long lessonId);
}
