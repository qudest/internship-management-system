package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с уроками
 */
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    Optional<LessonEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<LessonEntity> findAllByInternshipId(Long internshipId);
}
