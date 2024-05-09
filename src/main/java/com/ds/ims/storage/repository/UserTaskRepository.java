package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с заданиями пользователей
 */
public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
    Optional<UserTaskEntity> findById(Long id);

    List<UserTaskEntity> findAllByUserId(Long userId);

    Optional<UserTaskEntity> findByUserIdAndTaskId(Long userId, Long taskId);

    Optional<UserTaskEntity> findByForkedGitlabRepositoryUrl(String url);
}
