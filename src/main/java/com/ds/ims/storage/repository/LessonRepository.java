package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findAllByInternshipId(Long internshipId);
    Optional<LessonEntity> findByIdAndInternshipId(Long internshipId, Long id);
}
