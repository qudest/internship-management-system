package com.ds.ims.api.service;

import com.ds.ims.api.dto.AccountDto;
import com.ds.ims.api.dto.JwtRequestDto;
import com.ds.ims.api.dto.JwtResponseDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.api.exception.ErrorDto;
import com.ds.ims.api.utils.JwtTokenUtils;
import com.ds.ims.storage.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthService {
    AccountService accountService;
    JwtTokenUtils jwtTokenUtils;
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorDto(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = accountService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationAccountDto registrationAccountDto) {
        if (!registrationAccountDto.getPassword().equals(registrationAccountDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Password and confirm password do not match"), HttpStatus.BAD_REQUEST);
        }
        if (accountService.findByUsername(registrationAccountDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), "User with this username already exists"), HttpStatus.BAD_REQUEST);
        }
        //todo mapper
        AccountEntity account = accountService.createNewAccount(registrationAccountDto);
        return ResponseEntity.ok(new AccountDto(account.getId(), account.getUsername()));
    }

    public Long getAuthenticatedAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return accountService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found")).getId();
    }
}
