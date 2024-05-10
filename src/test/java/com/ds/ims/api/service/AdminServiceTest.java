package com.ds.ims.api.service;

import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.AdminEntity;
import com.ds.ims.storage.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminServiceTest {
    private AdminRepository adminRepository;
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminRepository = Mockito.mock(AdminRepository.class);
        adminService = new AdminService(adminRepository);
    }

    @Test
    void findByAccountIdReturnsAdmin() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAccount(accountEntity);
        when(adminRepository.findByAccountId(1L)).thenReturn(Optional.of(adminEntity));

        Optional<AdminEntity> result = adminService.findByAccountId(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getAccount().getId());
    }

    @Test
    void findByAccountIdReturnsEmptyWhenAdminNotFound() {
        when(adminRepository.findByAccountId(1L)).thenReturn(Optional.empty());

        Optional<AdminEntity> result = adminService.findByAccountId(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void findAllReturnsAdminList() {
        AccountEntity accountEntity1 = new AccountEntity();
        accountEntity1.setId(1L);
        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setId(2L);

        AdminEntity adminEntity1 = new AdminEntity();
        adminEntity1.setAccount(accountEntity1);
        AdminEntity adminEntity2 = new AdminEntity();
        adminEntity2.setAccount(accountEntity2);
        when(adminRepository.findAll()).thenReturn(Arrays.asList(adminEntity1, adminEntity2));

        List<AdminEntity> result = adminService.findAll();
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getAccount().getId());
        assertEquals(2L, result.get(1).getAccount().getId());
    }

    @Test
    void findAllReturnsEmptyListWhenNoAdmins() {
        when(adminRepository.findAll()).thenReturn(Collections.emptyList());

        List<AdminEntity> result = adminService.findAll();
        assertTrue(result.isEmpty());
    }
}