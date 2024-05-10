package com.ds.ims.api.service;

import com.ds.ims.api.dto.AccountDto;
import com.ds.ims.api.dto.JwtRequestDto;
import com.ds.ims.api.dto.JwtResponseDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.api.utils.JwtTokenUtils;
import com.ds.ims.storage.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    private AccountService accountService;
    private JwtTokenUtils jwtTokenUtils;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        accountService = Mockito.mock(AccountService.class);
        jwtTokenUtils = Mockito.mock(JwtTokenUtils.class);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        authService = new AuthService(accountService, jwtTokenUtils, authenticationManager);
    }

    @Test
    void createAuthTokenReturnsJwtResponse() {
        JwtRequestDto jwtRequestDto = new JwtRequestDto();
        jwtRequestDto.setUsername("testUser");
        jwtRequestDto.setPassword("testPassword");

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(accountService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(userDetails)).thenReturn("testToken");

        ResponseEntity<?> result = authService.createAuthToken(jwtRequestDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof JwtResponseDto);
        assertEquals("testToken", ((JwtResponseDto) result.getBody()).getToken());
    }

    @Test
    void createNewAccountReturnsCreatedAccount() {
        RegistrationAccountDto registrationAccountDto = new RegistrationAccountDto();
        registrationAccountDto.setUsername("testUser");
        registrationAccountDto.setPassword("testPassword");
        registrationAccountDto.setConfirmPassword("testPassword");

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testUser");
        when(accountService.findByUsername("testUser")).thenReturn(Optional.empty());
        when(accountService.createNewAccount(registrationAccountDto)).thenReturn(accountEntity);

        ResponseEntity<?> result = authService.createNewAccount(registrationAccountDto);
        assertNotNull(result);
        assertTrue(result.getBody() instanceof AccountDto);
        assertEquals("testUser", ((AccountDto) result.getBody()).getUsername());
    }

    @Test
    void createNewAccountThrowsBadRequestWhenPasswordsDoNotMatch() {
        RegistrationAccountDto registrationAccountDto = new RegistrationAccountDto();
        registrationAccountDto.setUsername("testUser");
        registrationAccountDto.setPassword("testPassword");
        registrationAccountDto.setConfirmPassword("differentPassword");

        assertThrows(BadRequestException.class, () -> authService.createNewAccount(registrationAccountDto));
    }

    @Test
    void createNewAccountThrowsBadRequestWhenUserAlreadyExists() {
        RegistrationAccountDto registrationAccountDto = new RegistrationAccountDto();
        registrationAccountDto.setUsername("testUser");
        registrationAccountDto.setPassword("testPassword");
        registrationAccountDto.setConfirmPassword("testPassword");

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testUser");
        when(accountService.findByUsername("testUser")).thenReturn(Optional.of(accountEntity));

        assertThrows(BadRequestException.class, () -> authService.createNewAccount(registrationAccountDto));
    }

    @Test
    void getAuthenticatedAccountIdReturnsAccountId() {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        when(accountService.findByUsername("testUser")).thenReturn(Optional.of(accountEntity));

        Long result = authService.getAuthenticatedAccountId();
        assertEquals(1L, result);
    }
}