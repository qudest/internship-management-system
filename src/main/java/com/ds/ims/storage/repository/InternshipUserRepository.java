package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с пользователями стажировок
 */
public interface InternshipUserRepository extends JpaRepository<InternshipUserEntity, Long> {
    List<InternshipUserEntity> findByUserAndStatus(UserEntity user, InternshipUserStatus status);
    Optional<InternshipUserEntity> findByUserIdAndInternshipId(Long userId, Long internshipId);
    List<InternshipUserEntity> findAllByInternshipIdAndStatus(Long internshipId, InternshipUserStatus internshipUserStatus);
}
