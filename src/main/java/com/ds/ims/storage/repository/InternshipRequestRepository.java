package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRequestRepository extends JpaRepository<InternshipRequestEntity, Long> {

}
