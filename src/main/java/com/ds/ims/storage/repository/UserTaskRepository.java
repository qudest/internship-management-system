package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
    List<UserTaskEntity> findAllByUserId(Long userId);
    UserTaskEntity findByUserIdAndTaskId(Long userId, Long taskId);
}
