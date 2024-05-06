package com.ds.ims.api.service;

import com.ds.ims.storage.entity.AdminEntity;
import com.ds.ims.storage.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AdminService {
    AdminRepository adminRepository;

    public AdminEntity findByAccountId(Long accountId) {
        return adminRepository.findByAccountId(accountId).get();
    }

    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }
}
