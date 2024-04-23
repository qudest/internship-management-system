package com.ds.ims.api.service;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.mapper.InternshipMapper;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.status.InternshipStatus;
import com.ds.ims.storage.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InternshipService {
    InternshipRepository internshipRepository;

    public Optional<InternshipDto> getInternshipById(Long id) {
        InternshipEntity internship = internshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Internship not found"));
        return Optional.of(InternshipMapper.INSTANCE.toDto(internship));
    }

    public List<InternshipDto> getInternshipsOpenForRegistration() {
        List<InternshipEntity> internships = internshipRepository.findAllByStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        return InternshipMapper.INSTANCE.toDtos(internships);
    }
}
