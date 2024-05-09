package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserDto;
import com.ds.ims.api.mapper.UserMapper;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

/**
 * Сервис для работы с пользователями
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    AccountService accountService;

    /**
     * Поиск пользователя по id аккаунта
     *
     * @param accountId - id аккаунта
     * @return - пользователь
     */
    public Optional<UserEntity> findByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    /**
     * Поиск пользователя по email
     *
     * @param email - email пользователя
     * @return - пользователь
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Создание нового пользователя
     *
     * @param accountId - id аккаунта
     * @param userDto   - данные пользователя
     * @return - созданный пользователь
     */
    public UserEntity createNewUser(Long accountId, UserDto userDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
        boolean exists = findByAccountId(accountId).isPresent();
        if (exists) {
            throw new BadRequestException("User already exists");
        }
        if (findByEmail(userDto.getEmail()).isPresent()) {
            throw new BadRequestException("User with email " + userDto.getEmail() + " already exists");
        }
        userEntity.setAccount(accountService.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id " + accountId + " not found")));
        return userRepository.save(userEntity);
    }

    /**
     * Обновление данных пользователя
     *
     * @param accountId - id аккаунта
     * @param userDto   - данные пользователя
     * @return - обновленный пользователь
     */
    public UserEntity updateUser(Long accountId, UserDto userDto) {
        UserEntity existingUser = findByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserMapper.INSTANCE.updateEntityFromDto(userDto, existingUser);
        return userRepository.save(existingUser);
    }

    /**
     * Создание или обновление пользователя
     *
     * @param accountId - id аккаунта
     * @param userDto   - данные пользователя
     * @return - созданный или обновленный пользователь
     */
    public UserEntity createOrUpdateUser(Long accountId, UserDto userDto) {
        if (findByAccountId(accountId).isPresent()) {
            return updateUser(accountId, userDto);
        } else {
            return createNewUser(accountId, userDto);
        }
    }
}
