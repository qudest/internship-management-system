package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRepository extends JpaRepository<InternshipEntity, Long> {
}
