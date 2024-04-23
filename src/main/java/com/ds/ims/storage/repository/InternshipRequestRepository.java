package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternshipRequestRepository extends JpaRepository<InternshipRequestEntity, Long> {
    Optional<InternshipRequestEntity> findByInternshipIdAndUserId(Long internshipId, Long userId);
}
