package com.ds.ims.api.service;

import com.ds.ims.api.dto.RequestDto;
import com.ds.ims.api.mapper.RequestMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.repository.InternshipRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipRequestService {
    InternshipUserService internshipUserService;
    InternshipRequestRepository internshipRequestRepository;

    public InternshipRequestEntity createInternshipRequest(InternshipEntity internship, UserEntity user) {
        Optional<InternshipRequestEntity> existingRequest = internshipRequestRepository.findByInternshipIdAndUserId(internship.getId(), user.getId());
        if (existingRequest.isPresent()) {
            return existingRequest.get();
        } else {
            //todo mapper
            InternshipRequestEntity internshipRequestEntity = new InternshipRequestEntity();
            internshipRequestEntity.setInternship(internship);
            internshipRequestEntity.setUser(user);
            internshipRequestEntity.setStatus(InternshipRequestStatus.PENDING);
            return internshipRequestRepository.save(internshipRequestEntity);
        }
    }

    public void deleteInternshipRequest(InternshipEntity internship, UserEntity user) {
        Optional<InternshipRequestEntity> existingRequest = internshipRequestRepository.findByInternshipIdAndUserId(internship.getId(), user.getId());
        if (existingRequest.get().getStatus() != InternshipRequestStatus.PENDING) {
            return;
        }
        existingRequest.ifPresent(internshipRequestRepository::delete);
    }

    public List<RequestDto> getPendingInternshipRequestsByInternshipId(Long internshipId) {
        List<InternshipRequestEntity> requests = internshipRequestRepository.findAllByInternshipIdAndStatus(internshipId, InternshipRequestStatus.PENDING);
        return RequestMapper.INSTANCE.toDtos(requests);
    }

    public InternshipRequestEntity findById(Long requestId) {
        return internshipRequestRepository.findById(requestId).get();
    }

    public UserEntity findUserByRequestId(Long requestId) {
        return findById(requestId).getUser();
    }

    public InternshipEntity findInternshipByRequestId(Long requestId) {
        return findById(requestId).getInternship();
    }

    public ResponseEntity<?> considerRequest(Long internshipId, Long requestId, boolean isAccepted) {
        InternshipRequestEntity request = findById(requestId);
        if (isAccepted) {
            RequestMapper.INSTANCE.updateEntityStatus(request, InternshipRequestStatus.ACCEPTED);
            InternshipUserEntity internshipUserEntity = new InternshipUserEntity();
            internshipUserEntity.setUser(findUserByRequestId(requestId));
            internshipUserEntity.setInternship(findInternshipByRequestId(requestId));
            internshipUserEntity.setStatus(InternshipUserStatus.ACTIVE);
            internshipUserEntity.setEntryDate(LocalDateTime.now());
            internshipUserService.createGitlabUser(findUserByRequestId(requestId));
            internshipUserService.save(internshipUserEntity);
        } else {
            RequestMapper.INSTANCE.updateEntityStatus(request, InternshipRequestStatus.REJECTED);
            //todo еще какая то логика?
        }
        internshipRequestRepository.save(request);
        return ResponseEntity.ok().build();
    }
}
