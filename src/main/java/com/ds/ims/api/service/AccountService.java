package com.ds.ims.api.service;

import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с аккаунтами
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountService implements UserDetailsService {
    AccountRepository accountRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;

    /**
     * Получение аккаунта по username
     *
     * @param username - имя пользователя
     * @return - аккаунт
     */
    public Optional<AccountEntity> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /**
     * Получение аккаунта по id
     *
     * @param id - id аккаунта
     * @return - аккаунт
     */
    public Optional<AccountEntity> findById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Получение UserDetails по username (Spring Security)
     *
     * @param s - имя пользователя
     * @return - UserDetails
     * @throws UsernameNotFoundException - исключение, если пользователь не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AccountEntity accountEntity = findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", s)));
        return new User(
                accountEntity.getUsername(),
                accountEntity.getPassword(),
                accountEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    /**
     * Создание нового аккаунта
     *
     * @param registrationAccountDto - данные для регистрации
     * @return - созданный аккаунт
     */
    public AccountEntity createNewAccount(RegistrationAccountDto registrationAccountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(registrationAccountDto.getUsername());
        accountEntity.setPassword(passwordEncoder.encode(registrationAccountDto.getPassword()));
        accountEntity.setRoles(Collections.singletonList(roleService.getUserRole()));
        return accountRepository.save(accountEntity);
    }
}
