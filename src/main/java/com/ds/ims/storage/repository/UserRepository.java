package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByAccountId(Long accountId);
}
