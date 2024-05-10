package com.ds.ims.api.service;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.storage.entity.InternshipEntity;
import com.ds.ims.storage.entity.status.InternshipStatus;
import com.ds.ims.storage.repository.InternshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class InternshipServiceTest {
    private InternshipRepository internshipRepository;
    private InternshipService internshipService;

    @BeforeEach
    void setUp() {
        internshipRepository = Mockito.mock(InternshipRepository.class);
        InternshipUserService internshipUserService = Mockito.mock(InternshipUserService.class);
        internshipService = new InternshipService(internshipRepository, internshipUserService);
    }

    @Test
    void findByIdReturnsInternship() {
        Long id = 1L;
        InternshipEntity internship = new InternshipEntity();
        when(internshipRepository.findById(id)).thenReturn(Optional.of(internship));

        Optional<InternshipEntity> result = internshipService.findById(id);
        assertTrue(result.isPresent());
        assertEquals(internship, result.get());
    }

    @Test
    void findByIdThrowsNotFoundWhenInternshipNotFound() {
        Long id = 1L;
        when(internshipRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> internshipService.getInternshipById(id));
    }

    @Test
    void findAllByStatusReturnsInternships() {
        List<InternshipEntity> internships = Arrays.asList(new InternshipEntity(), new InternshipEntity());
        when(internshipRepository.findAllByStatus(InternshipStatus.OPEN_FOR_REGISTRATION)).thenReturn(internships);

        List<InternshipEntity> result = internshipService.findAllByStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        assertEquals(2, result.size());
    }

    @Test
    void createInternshipCreatesNewInternship() {
        InternshipDto internshipDto = new InternshipDto();
        InternshipEntity internshipEntity = new InternshipEntity();
        when(internshipRepository.save(any(InternshipEntity.class))).thenReturn(internshipEntity);

        ResponseEntity<?> result = internshipService.createInternship(internshipDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof InternshipDto);
    }

    @Test
    void updateInternshipUpdatesExistingInternship() {
        Long internshipId = 1L;
        InternshipDto internshipDto = new InternshipDto();
        internshipDto.setStatus(InternshipStatus.OPEN_FOR_REGISTRATION);
        InternshipEntity existingInternship = new InternshipEntity();
        when(internshipService.findById(internshipId)).thenReturn(Optional.of(existingInternship));
        when(internshipRepository.save(any(InternshipEntity.class))).thenReturn(existingInternship);

        ResponseEntity<?> result = internshipService.updateInternship(internshipId, internshipDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof InternshipDto);
    }

    @Test
    void updateInternshipThrowsNotFoundWhenInternshipNotFound() {
        Long internshipId = 1L;
        InternshipDto internshipDto = new InternshipDto();
        when(internshipService.findById(internshipId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> internshipService.updateInternship(internshipId, internshipDto));
    }
}