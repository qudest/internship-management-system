package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.InternshipUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipUserRepository extends JpaRepository<InternshipUserEntity, Long> {
}
