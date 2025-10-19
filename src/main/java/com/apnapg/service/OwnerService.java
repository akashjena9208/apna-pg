package com.apnapg.service;

import com.apnapg.dto.OwnerRegistrationDTO;
import com.apnapg.entity.Owner;
import com.apnapg.entity.User;
import com.apnapg.enums.Role;
import com.apnapg.mapper.OwnerMapper;
import com.apnapg.repositories.OwnerRepository;
import com.apnapg.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Owner registerOwner(OwnerRegistrationDTO dto, byte[] profileImageBytes, String fileName) {
        log.info("Registering owner with email {}", dto.email());

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Owner with email " + dto.email() + " already exists.");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = User.builder()
                .email(dto.email())
                .password(encodedPassword)
                .role(Role.OWNER)
                .build();
        userRepository.save(user);

        String profileImageUrl = null;
        if (profileImageBytes != null && fileName != null && !fileName.isBlank()) {
            profileImageUrl = fileStorageService.saveFile(profileImageBytes, fileName);
        }

        Owner owner = OwnerMapper.toEntity(dto, profileImageUrl, user);
        return ownerRepository.save(owner);
    }

    public Owner getOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with email: " + email));
    }


}
