package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с заданиями
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findById(Long id);
}
