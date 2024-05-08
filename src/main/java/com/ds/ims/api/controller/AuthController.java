package com.ds.ims.api.controller;

import com.ds.ims.api.dto.JwtRequestDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.api.service.AuthService;
import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для авторизации и регистрации
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping(ApiPaths.BASE_API)
public class AuthController {
    AuthService authService;

    /**
     * Создание токена авторизации
     * POST /api/auth
     *
     * @param authRequest - данные для авторизации
     * @return - ответ с результатом авторизации
     */
    @PostMapping(ApiPaths.AUTH)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    /**
     * Регистрация нового пользователя
     * POST /api/register
     *
     * @param registrationAccountDto - данные для регистрации
     * @return - ответ с результатом регистрации
     */
    @PostMapping(ApiPaths.REGISTER)
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationAccountDto registrationAccountDto) {
        return authService.createNewAccount(registrationAccountDto);
    }
}
