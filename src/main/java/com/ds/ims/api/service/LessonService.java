package com.ds.ims.api.service;

import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.api.mapper.LessonMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.LessonEntity;
import com.ds.ims.storage.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с уроками
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LessonService {
    LessonRepository lessonRepository;
    InternshipUserService internshipUserService;
    InternshipService internshipService;

    /**
     * Получить все уроки по стажировке
     *
     * @param internshipId           - id стажировки
     * @param authenticatedAccountId - id пользователя
     * @return список уроков
     */
    public List<LessonDto> getLessons(Long internshipId, Long authenticatedAccountId) {
        internshipUserService.checkUserForMembership(internshipId, authenticatedAccountId);
        return LessonMapper.INSTANCE.toDtos(findAllByInternshipId(internshipId));
    }

    /**
     * Получить урок по id
     *
     * @param lessonId               - id урока
     * @param authenticatedAccountId - id пользователя
     * @return урок
     */
    public LessonDto getLesson(Long lessonId, Long authenticatedAccountId) {
        LessonEntity lessonEntity = findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson with id " + lessonId + " not found"));
        internshipUserService.checkUserForMembership(lessonEntity.getInternship().getId(), authenticatedAccountId);
        return LessonMapper.INSTANCE.toDto(lessonEntity);
    }

    /**
     * Найти урок по id
     *
     * @param id - id урока
     * @return урок
     */
    public Optional<LessonEntity> findById(Long id) {
        return lessonRepository.findById(id);
    }

    /**
     * Прооверить существование урока по id
     *
     * @param id - id урока
     * @return true, если урок существует, иначе false
     */
    public boolean existsById(Long id) {
        return lessonRepository.existsById(id);
    }

    /**
     * Найти все уроки по id стажировки
     *
     * @param internshipId - id стажировки
     * @return список уроков
     */
    public List<LessonEntity> findAllByInternshipId(Long internshipId) {
        return lessonRepository.findAllByInternshipId(internshipId);
    }

    /**
     * Создать урок
     *
     * @param internshipId - id стажировки
     * @param lessonDto    - данные для урока
     * @return созданный урок
     */
    public ResponseEntity<?> createLesson(Long internshipId, LessonDto lessonDto) {
        LessonEntity lessonEntity = LessonMapper.INSTANCE.toEntity(lessonDto);
        InternshipEntity existingInternship = internshipService.findById(internshipId)
                .orElseThrow(() -> new NotFoundException("Internship with id " + internshipId + " not found"));
        lessonEntity.setInternship(existingInternship);
        LessonEntity savedLesson = lessonRepository.save(lessonEntity);
        return ResponseEntity.ok(LessonMapper.INSTANCE.toDto(savedLesson));
    }

    /**
     * Обновить урок
     *
     * @param lessonId  - id урока
     * @param lessonDto - данные для урока
     * @return обновленный урок
     */
    public ResponseEntity<?> updateLesson(Long lessonId, LessonDto lessonDto) {
        LessonEntity existingLesson = findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson with id " + lessonId + " not found"));
        LessonMapper.INSTANCE.updateEntityFromDto(lessonDto, existingLesson);
        lessonRepository.save(existingLesson);
        return ResponseEntity.ok(LessonMapper.INSTANCE.toDto(existingLesson));
    }

    /**
     * Удалить урок
     *
     * @param lessonId - id урока
     * @return ответ
     */
    public ResponseEntity<?> deleteLesson(Long lessonId) {
        if (!existsById(lessonId)) {
            throw new NotFoundException("Lesson with id " + lessonId + " not found");
        }
        lessonRepository.deleteById(lessonId);
        return ResponseEntity.ok().build();
    }
}
