package com.ds.ims.api.service;

import com.ds.ims.api.dto.JwtRequestDto;
import com.ds.ims.api.dto.JwtResponseDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.api.mapper.AccountMapper;
import com.ds.ims.api.utils.JwtTokenUtils;
import com.ds.ims.storage.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * Сервис для работы с аутентификацией и авторизацией
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthService {
    AccountService accountService;
    JwtTokenUtils jwtTokenUtils;
    AuthenticationManager authenticationManager;

    /**
     * Создание JWT токена для аутентификации
     *
     * @param authRequest - запрос на аутентификацию
     * @return JWT токен
     */
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
        UserDetails userDetails = accountService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    /**
     * Создание нового аккаунта
     *
     * @param registrationAccountDto - данные для регистрации
     * @return созданный аккаунт
     */
    public ResponseEntity<?> createNewAccount(@RequestBody RegistrationAccountDto registrationAccountDto) {
        if (!registrationAccountDto.getPassword().equals(registrationAccountDto.getConfirmPassword())) {
            throw new BadRequestException("Password and confirm password do not match");
        }
        if (accountService.findByUsername(registrationAccountDto.getUsername()).isPresent()) {
            throw new BadRequestException("User with this username already exists");
        }
        AccountEntity account = accountService.createNewAccount(registrationAccountDto);
        return ResponseEntity.ok(AccountMapper.INSTANCE.toDto(account));
    }

    /**
     * Получение id аутентифицированного аккаунта
     *
     * @return id аутентифицированного аккаунта
     */
    public Long getAuthenticatedAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return accountService.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Account with username + " + username + " not found")).getId();
    }
}
