package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с аккаунтами
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findById(Long id);
}
