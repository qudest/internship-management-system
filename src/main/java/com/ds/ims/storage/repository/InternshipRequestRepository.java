package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с заявками на стажировку
 */
public interface InternshipRequestRepository extends JpaRepository<InternshipRequestEntity, Long> {
    Optional<InternshipRequestEntity> findByInternshipAndUser(InternshipEntity internship, UserEntity user);

    List<InternshipRequestEntity> findAllByInternshipIdAndStatus(Long internshipId, InternshipRequestStatus status);

    Optional<InternshipRequestEntity> findById(Long id);
}
