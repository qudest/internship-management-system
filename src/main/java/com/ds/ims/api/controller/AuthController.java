package com.ds.ims.api.controller;

import com.ds.ims.api.dto.JwtRequestDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.api.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationAccountDto registrationAccountDto) {
        return authService.createNewUser(registrationAccountDto);
    }
}
