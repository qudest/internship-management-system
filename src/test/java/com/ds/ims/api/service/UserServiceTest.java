package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserDto;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserRepository userRepository;
    private AccountService accountService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        accountService = Mockito.mock(AccountService.class);
        userService = new UserService(userRepository, accountService);
    }

    @Test
    void findByAccountIdReturnsUserEntity() {
        Long accountId = 1L;
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(new UserEntity()));

        Optional<UserEntity> result = userService.findByAccountId(accountId);
        assertTrue(result.isPresent());
    }

    @Test
    void findByAccountIdReturnsEmptyWhenUserNotFound() {
        Long accountId = 1L;
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.findByAccountId(accountId);
        assertFalse(result.isPresent());
    }

    @Test
    void createNewUserReturnsCreatedUser() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(accountService.findById(accountId)).thenReturn(Optional.of(new AccountEntity()));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(new UserEntity());

        UserEntity result = userService.createNewUser(accountId, userDto);
        assertNotNull(result);
    }

    @Test
    void createNewUserThrowsBadRequestWhenUserExists() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(new UserEntity()));

        assertThrows(BadRequestException.class, () -> userService.createNewUser(accountId, userDto));
    }

    @Test
    void createNewUserThrowsBadRequestWhenEmailExists() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new UserEntity()));

        assertThrows(BadRequestException.class, () -> userService.createNewUser(accountId, userDto));
    }

    @Test
    void createNewUserThrowsNotFoundWhenAccountNotFound() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(accountService.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.createNewUser(accountId, userDto));
    }

    @Test
    void updateUserReturnsUpdatedUser() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(new UserEntity()));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(new UserEntity());

        UserEntity result = userService.updateUser(accountId, userDto);
        assertNotNull(result);
    }

    @Test
    void updateUserThrowsNotFoundWhenUserNotFound() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(accountId, userDto));
    }

    @Test
    void createOrUpdateUserReturnsUpdatedUser() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(new UserEntity()));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(new UserEntity());

        UserEntity result = userService.createOrUpdateUser(accountId, userDto);
        assertNotNull(result);
    }

    @Test
    void createOrUpdateUserReturnsCreatedUser() {
        Long accountId = 1L;
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(accountService.findById(accountId)).thenReturn(Optional.of(new AccountEntity()));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(new UserEntity());

        UserEntity result = userService.createOrUpdateUser(accountId, userDto);
        assertNotNull(result);
    }

}