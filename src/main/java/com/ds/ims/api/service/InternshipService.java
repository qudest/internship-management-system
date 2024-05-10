package com.ds.ims.api.service;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.mapper.InternshipMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.status.InternshipStatus;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с стажировками
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipService {
    InternshipRepository internshipRepository;
    InternshipUserService internshipUserService;

    /**
     * Поиск стажировки по id
     *
     * @param id - id стажировки
     * @return - стажировка
     */
    public Optional<InternshipEntity> findById(Long id) {
        return internshipRepository.findById(id);
    }

    /**
     * Поиск всех стажировок по статусу
     *
     * @param status - статус стажировки
     * @return - список стажировок
     */
    public List<InternshipEntity> findAllByStatus(InternshipStatus status) {
        return internshipRepository.findAllByStatus(status);
    }

    /**
     * Получение стажировки по id
     *
     * @param id - id стажировки
     * @return - стажировка
     */
    public InternshipDto getInternshipById(Long id) {
        InternshipEntity internship = findById(id).orElseThrow(() -> new NotFoundException("Internship with id " + id + " not found"));
        return InternshipMapper.INSTANCE.toDto(internship);
    }

    /**
     * Получение всех стажировок, открытых для регистрации
     *
     * @return - список стажировок
     */
    public List<InternshipDto> getInternshipsOpenForRegistration() {
        List<InternshipEntity> internships = findAllByStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        return InternshipMapper.INSTANCE.toDtos(internships);
    }

    /**
     * Создание стажировки
     *
     * @param internshipDto - данные стажировки
     * @return - ответ
     */
    public ResponseEntity<?> createInternship(InternshipDto internshipDto) {
        InternshipEntity internshipEntity = InternshipMapper.INSTANCE.toEntity(internshipDto);
        internshipEntity.setStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        InternshipEntity savedInternship = internshipRepository.save(internshipEntity);
        return ResponseEntity.ok(InternshipMapper.INSTANCE.toDto(savedInternship));
    }

    /**
     * Обновление стажировки
     *
     * @param internshipId - id стажировки
     * @param internshipDto - данные стажировки
     * @return - ответ
     */
    public ResponseEntity<?> updateInternship(Long internshipId, InternshipDto internshipDto) {
        InternshipEntity existingInternship = findById(internshipId)
                .orElseThrow(() -> new NotFoundException("Internship with id " + internshipId + " not found"));
        InternshipMapper.INSTANCE.updateEntityFromDto(internshipDto, existingInternship);

        if (internshipDto.getStatus().equals(InternshipStatus.ENDED)) {
            List<InternshipUserEntity> internshipUsers = internshipUserService.findAllByInternshipIdAndStatus(
                    existingInternship.getId(), InternshipUserStatus.ACTIVE
            );
            for (InternshipUserEntity internshipUser : internshipUsers) {
                internshipUserService.updateStatus(internshipUser, InternshipUserStatus.COMPLETED);
            }
        }

        internshipRepository.save(existingInternship);
        return ResponseEntity.ok(InternshipMapper.INSTANCE.toDto(existingInternship));
    }
}
