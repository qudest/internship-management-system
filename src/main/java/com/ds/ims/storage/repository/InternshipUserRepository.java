package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternshipUserRepository extends JpaRepository<InternshipUserEntity, Long> {
    List<InternshipUserEntity> findByUserIdAndStatus(Long userId, InternshipUserStatus status);
    Optional<InternshipUserEntity> findByUserIdAndInternshipId(Long userId, Long internshipId);

}
