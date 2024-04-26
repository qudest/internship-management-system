package com.ds.ims.api.service;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.dto.UserDto;
import com.ds.ims.api.mapper.InternshipMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.InternshipUserEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.status.InternshipStatus;
import com.ds.ims.storage.entity.status.InternshipUserStatus;
import com.ds.ims.storage.repository.InternshipRepository;
import com.ds.ims.storage.repository.InternshipUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipService {
    UserService userService;
    InternshipRepository internshipRepository;
    InternshipUserService internshipUserService;
    InternshipRequestService internshipRequestService;

    public Optional<InternshipDto> getInternshipById(Long id) {
        InternshipEntity internship = internshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Internship not found"));
        return Optional.of(InternshipMapper.INSTANCE.toDto(internship));
    }

    public List<InternshipDto> getInternshipsOpenForRegistration() {
        List<InternshipEntity> internships = internshipRepository.findAllByStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        return InternshipMapper.INSTANCE.toDtos(internships);
    }

    //todo в internshipUserEntity перенести
    public List<InternshipDto> getInternshipsForUser(Long accountId) {
        Long userId = userService.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("User not found")).getId();
        List<InternshipUserEntity> internshipUserEntities = internshipUserService.findByUserIdAndStatus(userId, InternshipUserStatus.ACTIVE);
        List<InternshipDto> internships = new ArrayList<>();
        for (InternshipUserEntity internshipUserEntity: internshipUserEntities) {
            System.out.println(internshipUserEntity.getInternship().getId());
            internships.add(InternshipMapper.INSTANCE.toDto(internshipUserEntity.getInternship()));
        }
        return internships;
    }
    //todo в InternshipRequestService перенести
    public ResponseEntity<?> registerToInternship(Long internshipId, Long accountId, UserDto userDto) {
        InternshipEntity internship = internshipRepository.findById(internshipId).orElseThrow(() -> new RuntimeException("Internship not found"));

        if (!internship.getStatus().equals(InternshipStatus.OPEN_FOR_REGISTRATION)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userService.createOrUpdateUser(accountId, userDto);
        internshipRequestService.createInternshipRequest(internship, userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //todo в InternshipRequestService перенести
    public ResponseEntity<?> deleteRequestToInternship(Long internshipId, Long accountId) {
        InternshipEntity internship = internshipRepository.findById(internshipId).orElseThrow(() -> new RuntimeException("Internship not found"));

        if (!internship.getStatus().equals(InternshipStatus.OPEN_FOR_REGISTRATION)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("User not found"));

        internshipRequestService.deleteInternshipRequest(internship, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> createInternship(InternshipDto internshipDto) {
        System.out.println(internshipDto);
        InternshipEntity internship = InternshipMapper.INSTANCE.toEntity(internshipDto);
        System.out.printf(internship.toString());
        internship.setStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        internshipRepository.save(internship);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
