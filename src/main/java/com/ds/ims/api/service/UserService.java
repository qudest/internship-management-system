package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserDto;
import com.ds.ims.api.mapper.UserMapper;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    AccountService accountService;

    public Optional<UserEntity> findByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    public UserEntity createNewUser(Long accountId, UserDto userDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
        boolean exists = findByAccountId(accountId).isPresent();
        if (exists) {
            throw new RuntimeException("User already exists");
        }
        userEntity.setAccount(accountService.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found")));
        return userRepository.save(userEntity);
    }

    public void updateUser(Long accountId, UserDto userDto) {
        UserEntity existingUser = findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserMapper.INSTANCE.updateEntityFromDto(userDto, existingUser);
        userRepository.save(existingUser);
    }

    public UserEntity createOrUpdateUser(Long accountId, UserDto userDto) {
        if (findByAccountId(accountId).isPresent()) {
            updateUser(accountId, userDto);
            return findByAccountId(accountId).get();
        } else {
            return createNewUser(accountId, userDto);
        }
    }
}
