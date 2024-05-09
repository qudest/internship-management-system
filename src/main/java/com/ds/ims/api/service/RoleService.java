package com.ds.ims.api.service;

import com.ds.ims.storage.entity.RoleEntity;
import com.ds.ims.storage.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

/**
 * Сервис для работы с ролями пользователей
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleService {
    RoleRepository roleRepository;

    /**
     * Получение роли пользователя
     *
     * @return роль пользователя
     */
    public RoleEntity getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("Role not found"));
    }

    /**
     * Получение роли администратора
     *
     * @return роль администратора
     */
    public RoleEntity getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
