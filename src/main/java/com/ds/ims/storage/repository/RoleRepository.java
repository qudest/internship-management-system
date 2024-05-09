package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с ролями пользователей
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(String name);
}
