package com.ds.ims.api.service;

import com.ds.ims.api.dto.AccountDto;
import com.ds.ims.api.dto.RegistrationAccountDto;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.repository.AccountRepository;
import com.ds.ims.storage.repository.RoleRepository;
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

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AccountService implements UserDetailsService {

    AccountRepository accountRepository;

    RoleService roleService;

    PasswordEncoder passwordEncoder;

    public Optional<AccountEntity> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AccountEntity accountEntity = findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", s)));
        return new User(
                accountEntity.getUsername(),
                accountEntity.getPassword(),
                //todo сделать маппер
                accountEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public AccountEntity createNewUser(RegistrationAccountDto registrationAccountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(registrationAccountDto.getUsername());
        accountEntity.setPassword(passwordEncoder.encode(registrationAccountDto.getPassword()));
        accountEntity.setRoles(Collections.singletonList(roleService.getUserRole()));
        return accountRepository.save(accountEntity);
    }
}
