package com.ds.ims.api.service;

import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import com.ds.ims.storage.repository.InternshipRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipRequestService {
    InternshipRequestRepository internshipRequestRepository;

    public InternshipRequestEntity createInternshipRequest(InternshipEntity internship, UserEntity user) {
        Optional<InternshipRequestEntity> existingRequest = internshipRequestRepository.findByInternshipIdAndUserId(internship.getId(), user.getId());
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
}
