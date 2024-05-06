package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long>{
    Optional<AdminEntity> findByAccountId(Long accountId);
}
