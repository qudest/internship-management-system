package com.ds.ims.api.service;

import com.ds.ims.storage.entity.RoleEntity;
import com.ds.ims.storage.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleService {
    RoleRepository roleRepository;

    public RoleEntity getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    public RoleEntity getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }
}
