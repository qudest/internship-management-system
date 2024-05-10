package com.ds.ims.api.service;

import com.ds.ims.storage.entity.RoleEntity;
import com.ds.ims.storage.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RoleServiceTest {
    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void getUserRole() {
        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));

        RoleEntity result = roleService.getUserRole();
        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
    }

    @Test
    void getUserRoleNotFound() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.getUserRole());
    }

    @Test
    void getAdminRole() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ROLE_ADMIN");
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));

        RoleEntity result = roleService.getAdminRole();
        assertNotNull(result);
        assertEquals("ROLE_ADMIN", result.getName());
    }

    @Test
    void getAdminRoleNotFound() {
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.getAdminRole());
    }
}