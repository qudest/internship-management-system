package com.ds.ims.api.service;

import com.ds.ims.storage.entity.AdminEntity;
import com.ds.ims.storage.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с администраторами
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AdminService {
    AdminRepository adminRepository;

    /**
     * Получение администратора по id аккаунта
     *
     * @param accountId - id аккаунта
     * @return - администратор
     */
    public Optional<AdminEntity> findByAccountId(Long accountId) {
        return adminRepository.findByAccountId(accountId);
    }

    /**
     * Получение всех администраторов
     *
     * @return - список администраторов
     */
    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }
}
