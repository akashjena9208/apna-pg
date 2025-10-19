package com.apnapg.service;

import com.apnapg.dto.TenantRegistrationDTO;
import com.apnapg.entity.Tenant;
import com.apnapg.entity.User;
import com.apnapg.enums.Role;
import com.apnapg.mapper.TenantMapper;
import com.apnapg.repositories.TenantRepository;
import com.apnapg.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Tenant registerTenant(TenantRegistrationDTO dto, byte[] aadhaarFileBytes, String fileName) {
        log.info("Registering tenant with email {}", dto.email());

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("User with email " + dto.email() + " already exists.");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());
        User user = User.builder()
                .email(dto.email())
                .password(encryptedPassword)
                .role(Role.TENANT)
                .build();
        userRepository.save(user);

        String aadhaarUrl = fileStorageService.saveFile(aadhaarFileBytes, fileName);
        Tenant tenant = TenantMapper.toEntity(dto, aadhaarUrl, user);
        Tenant savedTenant = tenantRepository.save(tenant);

        log.info("Tenant registered successfully with id {}", savedTenant.getId());
        return savedTenant;
    }
}
