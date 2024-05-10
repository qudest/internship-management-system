package com.ds.ims.api.service;

import com.ds.ims.storage.entity.UserTaskEntity;
import com.ds.ims.storage.repository.UserTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LastCheckDateServiceTest {
    private UserTaskRepository userTaskRepository;
    private LastCheckDateService lastCheckDateService;

    @BeforeEach
    void setUp() {
        userTaskRepository = Mockito.mock(UserTaskRepository.class);
        lastCheckDateService = new LastCheckDateService(userTaskRepository);
    }

    @Test
    void getLastCheckDateByUrlReturnsDate() {
        String url = "testUrl";
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setLastCheckDate(new Date());
        when(userTaskRepository.findByForkedGitlabRepositoryUrl(url)).thenReturn(Optional.of(userTaskEntity));

        Date result = lastCheckDateService.getLastCheckDateByUrl(url);
        assertNotNull(result);
        assertEquals(userTaskEntity.getLastCheckDate(), result);
    }

    @Test
    void getLastCheckDateByUrlThrowsNotFoundWhenUrlNotFound() {
        String url = "testUrl";
        when(userTaskRepository.findByForkedGitlabRepositoryUrl(url)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lastCheckDateService.getLastCheckDateByUrl(url));
    }

    @Test
    void updateLastCheckDateByUrlUpdatesDate() {
        String url = "testUrl";
        Date date = new Date();
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        when(userTaskRepository.findByForkedGitlabRepositoryUrl(url)).thenReturn(Optional.of(userTaskEntity));
        when(userTaskRepository.save(userTaskEntity)).thenReturn(userTaskEntity);

        lastCheckDateService.updateLastCheckDateByUrl(url, date);
        assertEquals(date, userTaskEntity.getLastCheckDate());
    }

    @Test
    void updateLastCheckDateByUrlThrowsNotFoundWhenUrlNotFound() {
        String url = "testUrl";
        Date date = new Date();
        when(userTaskRepository.findByForkedGitlabRepositoryUrl(url)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lastCheckDateService.updateLastCheckDateByUrl(url, date));
    }
}