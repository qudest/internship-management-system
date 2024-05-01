package com.ds.ims.api.service;

import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.UserEntity;
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
    GitlabService gitlabService;
    InternshipUserRepository internshipUserRepository;

    public List<InternshipUserEntity> findByUserIdAndStatus(Long userId, InternshipUserStatus internshipUserStatus) {
        return internshipUserRepository.findByUserIdAndStatus(userId, internshipUserStatus);
    }

    public Optional<InternshipUserEntity> findByUserIdAndInternshipId(Long userId, Long internshipId) {
        return internshipUserRepository.findByUserIdAndInternshipId(userId, internshipId);
    }

    public List<InternshipUserEntity> findAllByInternshipIdAndStatus(Long internshipId, InternshipUserStatus internshipUserStatus) {
        return internshipUserRepository.findAllByInternshipIdAndStatus(internshipId, internshipUserStatus);
    }

    public void save(InternshipUserEntity internshipUserEntity) {
        internshipUserRepository.save(internshipUserEntity);
    }

    public void createGitlabUser(UserEntity user) {
        gitlabService.createUser(user);
    }
}
