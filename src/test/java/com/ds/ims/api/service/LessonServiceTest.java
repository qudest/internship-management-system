package com.ds.ims.api.service;

import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.LessonEntity;
import com.ds.ims.storage.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LessonServiceTest {
    private LessonRepository lessonRepository;
    private InternshipService internshipService;
    private LessonService lessonService;

    @BeforeEach
    void setUp() {
        lessonRepository = Mockito.mock(LessonRepository.class);
        InternshipUserService internshipUserService = Mockito.mock(InternshipUserService.class);
        internshipService = Mockito.mock(InternshipService.class);
        lessonService = new LessonService(lessonRepository, internshipUserService, internshipService);
    }

    @Test
    void getLessonsReturnsListOfLessons() {
        Long internshipId = 1L;
        Long accountId = 1L;
        when(lessonRepository.findAllByInternshipId(internshipId)).thenReturn(Collections.singletonList(new LessonEntity()));

        List<LessonDto> result = lessonService.getLessons(internshipId, accountId);
        assertFalse(result.isEmpty());
    }

    @Test
    void getLessonReturnsLessonDto() {
        Long lessonId = 1L;
        Long accountId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setInternship(new InternshipEntity());
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));

        LessonDto result = lessonService.getLesson(lessonId, accountId);
        assertNotNull(result);
    }

    @Test
    void getLessonThrowsNotFoundWhenLessonNotFound() {
        Long lessonId = 1L;
        Long accountId = 1L;
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lessonService.getLesson(lessonId, accountId));
    }

    @Test
    void createLessonReturnsCreatedLesson() {
        Long internshipId = 1L;
        LessonDto lessonDto = new LessonDto();
        when(internshipService.findById(internshipId)).thenReturn(Optional.of(new InternshipEntity()));
        when(lessonRepository.save(Mockito.any(LessonEntity.class))).thenReturn(new LessonEntity());

        ResponseEntity<?> result = lessonService.createLesson(internshipId, lessonDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof LessonDto);
    }

    @Test
    void createLessonThrowsNotFoundWhenInternshipNotFound() {
        Long internshipId = 1L;
        LessonDto lessonDto = new LessonDto();
        when(internshipService.findById(internshipId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lessonService.createLesson(internshipId, lessonDto));
    }

    @Test
    void updateLessonReturnsUpdatedLesson() {
        Long lessonId = 1L;
        LessonDto lessonDto = new LessonDto();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(new LessonEntity()));
        when(lessonRepository.save(Mockito.any(LessonEntity.class))).thenReturn(new LessonEntity());

        ResponseEntity<?> result = lessonService.updateLesson(lessonId, lessonDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof LessonDto);
    }

    @Test
    void updateLessonThrowsNotFoundWhenLessonNotFound() {
        Long lessonId = 1L;
        LessonDto lessonDto = new LessonDto();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lessonService.updateLesson(lessonId, lessonDto));
    }
}