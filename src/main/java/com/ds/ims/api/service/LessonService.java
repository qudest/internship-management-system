package com.ds.ims.api.service;

import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.api.mapper.LessonMapper;
import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.LessonEntity;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LessonService {
    LessonRepository lessonRepository;
    InternshipUserService internshipUserService;
    UserService userService;
    InternshipService internshipService;

    public List<LessonDto> getLessons(Long internshipId, Long authenticatedAccountId) {
        userCheck(internshipId, authenticatedAccountId);

        return LessonMapper.INSTANCE.toDtos(lessonRepository.findAllByInternshipId(internshipId));
    }

    public LessonDto getLesson(Long internshipId, Long lessonId, Long authenticatedAccountId) {
        userCheck(internshipId, authenticatedAccountId);
        return LessonMapper.INSTANCE.toDto(findByIdAndInternshipId(lessonId, internshipId));
    }

    public LessonEntity findByIdAndInternshipId(Long internshipId, Long lessonId) {
        return lessonRepository.findByIdAndInternshipId(lessonId, internshipId);
    }

    //todo перенести в internshipUserService
    private void userCheck(Long internshipId, Long authenticatedAccountId) {
        Long userId = userService.findByAccountId(authenticatedAccountId).orElseThrow(() -> new RuntimeException("User not found")).getId();
        Optional<InternshipUserEntity> internshipUserEntity = internshipUserService.findByUserIdAndInternshipId(userId, internshipId);
        if (!internshipUserEntity.isPresent()) {
            throw new RuntimeException("User not registered to this internship");
        }
        if (internshipUserEntity.get().getStatus().equals(InternshipUserStatus.EXPELLED)) {
            throw new RuntimeException("User expelled from internship");
        }
    }

    public ResponseEntity<?> createLesson(Long internshipId, LessonDto lessonDto) {
        LessonEntity lessonEntity = LessonMapper.INSTANCE.toEntity(lessonDto);
        lessonEntity.setInternship(internshipService.getInternshipEntityById(internshipId));
        lessonRepository.save(lessonEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateLesson(Long id, Long lessonId, LessonDto lessonDto) {
        LessonEntity existingLesson = findByIdAndInternshipId(id, lessonId);
        LessonMapper.INSTANCE.updateEntityFromDto(lessonDto, existingLesson);
        lessonRepository.save(existingLesson);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteLesson(Long id, Long lessonId) {
        LessonEntity lessonEntity = findByIdAndInternshipId(id, lessonId);
        lessonRepository.delete(lessonEntity);
        return ResponseEntity.ok().build();
    }
}
