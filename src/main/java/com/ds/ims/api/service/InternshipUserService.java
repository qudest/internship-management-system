package com.ds.ims.api.service;

import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.repository.InternshipUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipUserService {
    InternshipUserRepository internshipUserRepository;

    public List<InternshipUserEntity> findByUserIdAndStatus(Long userId, InternshipUserStatus internshipUserStatus) {
        return internshipUserRepository.findByUserIdAndStatus(userId, internshipUserStatus);
    }

    public Optional<InternshipUserEntity> findByUserIdAndInternshipId(Long userId, Long internshipId) {
        return internshipUserRepository.findByUserIdAndInternshipId(userId, internshipId);
    }
}
