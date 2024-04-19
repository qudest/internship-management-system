package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
}
