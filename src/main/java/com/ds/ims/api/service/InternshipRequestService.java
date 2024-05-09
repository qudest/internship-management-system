package com.ds.ims.api.service;

import com.ds.ims.api.dto.RequestDto;
import com.ds.ims.api.dto.UserDto;
import com.ds.ims.api.mapper.RequestMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import com.ds.ims.storage.entity.status.InternshipStatus;
import com.ds.ims.storage.repository.InternshipRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с заявками на стажировку
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipRequestService {
    InternshipUserService internshipUserService;
    InternshipRequestRepository internshipRequestRepository;
    InternshipService internshipService;
    UserService userService;

    /**
     * Создание заявки на стажировку
     *
     * @param internship - стажировка
     * @param user       - пользователь
     * @return - созданная заявка
     */
    public InternshipRequestEntity createInternshipRequest(InternshipEntity internship, UserEntity user) {
        Optional<InternshipRequestEntity> existingRequest = findByInternshipIdAndUserId(internship, user);
        if (existingRequest.isPresent()) {
            return existingRequest.get();
        } else {
            InternshipRequestEntity internshipRequestEntity = new InternshipRequestEntity();
            internshipRequestEntity.setInternship(internship);
            internshipRequestEntity.setUser(user);
            internshipRequestEntity.setStatus(InternshipRequestStatus.PENDING);
            return internshipRequestRepository.save(internshipRequestEntity);
        }
    }

    /**
     * Удаление заявки на стажировку
     *
     * @param internship - стажировка
     * @param user       - пользователь
     */
    public void deleteInternshipRequest(InternshipEntity internship, UserEntity user) {
        InternshipRequestEntity existingRequest = findByInternshipIdAndUserId(internship, user)
                .orElseThrow(() -> new NotFoundException("Request not found"));
        if (!existingRequest.getStatus().equals(InternshipRequestStatus.PENDING)) {
            throw new BadRequestException("Request is already considered");
        }
        internshipRequestRepository.delete(existingRequest);
    }

    /**
     * Поиск заявки на стажировку по стажировке и пользователю
     *
     * @param internship - стажировка
     * @param user       - пользователь
     * @return - заявка на стажировку
     */
    public Optional<InternshipRequestEntity> findByInternshipIdAndUserId(InternshipEntity internship, UserEntity user) {
        return internshipRequestRepository.findByInternshipAndUser(internship, user);
    }

    /**
     * Получение всех ожидающих рассмотрения заявок на стажировку
     *
     * @param internshipId - id стажировки
     * @return - список заявок на стажировку
     */
    public List<RequestDto> getPendingInternshipRequests(Long internshipId) {
        List<InternshipRequestEntity> requests = findAllByInternshipIdAndStatus(internshipId, InternshipRequestStatus.PENDING);
        return RequestMapper.INSTANCE.toDtos(requests);
    }

    /**
     * Получение всех заявок на стажировку по статусу
     *
     * @param internshipId - id стажировки
     * @param status       - статус заявки
     * @return - список заявок на стажировку
     */
    public List<InternshipRequestEntity> findAllByInternshipIdAndStatus(Long internshipId, InternshipRequestStatus status) {
        return internshipRequestRepository.findAllByInternshipIdAndStatus(internshipId, status);
    }

    /**
     * Поиск заявки на стажировку по id
     *
     * @param requestId - id заявки
     * @return - заявка на стажировку
     */
    public Optional<InternshipRequestEntity> findById(Long requestId) {
        return internshipRequestRepository.findById(requestId);
    }

    /**
     * Рассмотрение заявки на стажировку
     *
     * @param requestId  - id заявки
     * @param isAccepted - принята ли заявка
     * @return - ответ
     */
    public ResponseEntity<?> considerRequest(Long requestId, boolean isAccepted) {
        InternshipRequestEntity request = findById(requestId).orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));

        if (isAccepted) {
            RequestMapper.INSTANCE.updateEntityStatus(request, InternshipRequestStatus.ACCEPTED);
            internshipUserService.createInternshipUser(request.getInternship(), request.getUser());
        } else {
            RequestMapper.INSTANCE.updateEntityStatus(request, InternshipRequestStatus.REJECTED);
        }

        internshipRequestRepository.save(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Регистрация на стажировку
     *
     * @param internshipId - id стажировки
     * @param accountId    - id аккаунта
     * @param userDto      - данные пользователя
     * @return - ответ
     */
    public ResponseEntity<?> registerToInternship(Long internshipId, Long accountId, UserDto userDto) {
        InternshipEntity internship = internshipService.findById(internshipId)
                .orElseThrow(() -> new NotFoundException("Internship with id " + internshipId + " not found"));

        if (!internship.getStatus().equals(InternshipStatus.OPEN_FOR_REGISTRATION)) {
            throw new BadRequestException("Internship is not open for registration");
        }

        UserEntity userEntity = userService.createOrUpdateUser(accountId, userDto);
        InternshipRequestEntity internshipRequest = createInternshipRequest(internship, userEntity);
        return ResponseEntity.ok(RequestMapper.INSTANCE.toDto(internshipRequest));
    }

    /**
     * Отмена заявки на стажировку
     *
     * @param internshipId - id стажировки
     * @param accountId    - id аккаунта
     * @return - ответ
     */
    public ResponseEntity<?> deleteRequestToInternship(Long internshipId, Long accountId) {
        InternshipEntity internship = internshipService.findById(internshipId)
                .orElseThrow(() -> new NotFoundException("Internship with id " + internshipId + " not found"));

        if (!internship.getStatus().equals(InternshipStatus.OPEN_FOR_REGISTRATION)) {
            throw new BadRequestException("Internship is not open for registration");
        }

        UserEntity user = userService.findByAccountId(accountId).orElseThrow(() -> new NotFoundException("User not found"));
        deleteInternshipRequest(internship, user);
        return ResponseEntity.ok().build();
    }
}
