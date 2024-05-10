package com.ds.ims.api.service;

import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.RoleEntity;
import com.ds.ims.storage.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountRepository accountRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        roleService = Mockito.mock(RoleService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        accountService = new AccountService(accountRepository, roleService, passwordEncoder);
    }

    @Test
    void findByUsernameReturnsAccount() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testUser");
        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.of(accountEntity));

        Optional<AccountEntity> result = accountService.findByUsername("testUser");
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void findByUsernameReturnsEmptyWhenAccountNotFound() {
        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        Optional<AccountEntity> result = accountService.findByUsername("testUser");
        assertFalse(result.isPresent());
    }

    @Test
    void loadUserByUsernameReturnsUserDetails() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testUser");
        accountEntity.setPassword("testPassword");
        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_USER");
        accountEntity.setRoles(Collections.singletonList(userRole));
        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.of(accountEntity));


        UserDetails result = accountService.loadUserByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("testPassword", result.getPassword());
        assertEquals("ROLE_USER", result.getAuthorities().stream().findFirst().get().getAuthority());
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserNotFound() {
        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("testUser"));
    }

    @Test
    void createNewAccountReturnsCreatedAccount() {
        RegistrationAccountDto registrationAccountDto = new RegistrationAccountDto();
        registrationAccountDto.setUsername("testUser");
        registrationAccountDto.setPassword("testPassword");

        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_USER");
        when(roleService.getUserRole()).thenReturn(userRole);
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testUser");
        accountEntity.setPassword("encodedPassword");
        accountEntity.setRoles(Collections.singletonList(userRole));
        when(accountRepository.save(Mockito.any(AccountEntity.class))).thenReturn(accountEntity);

        AccountEntity result = accountService.createNewAccount(registrationAccountDto);
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("ROLE_USER", result.getRoles().get(0).getName());
    }
}