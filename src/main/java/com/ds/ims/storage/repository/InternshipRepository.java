package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.status.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternshipRepository extends JpaRepository<InternshipEntity, Long> {
    Optional<InternshipEntity> findById(Long id);
    List<InternshipEntity> findAllByStatus(InternshipStatus status);
}
