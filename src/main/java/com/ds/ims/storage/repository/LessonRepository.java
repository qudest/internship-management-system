package com.ds.ims.storage.repository;

import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.storage.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findAllByInternshipId(Long internshipId);
    LessonEntity findByIdAndInternshipId(Long id, Long internshipId);
}
