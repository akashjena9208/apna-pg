package com.apnapg.service.impl;

import com.apnapg.dto.owner.*;
import com.apnapg.entity.Owner;
import com.apnapg.entity.PG;
import com.apnapg.entity.User;
import com.apnapg.enums.AuthProvider;
import com.apnapg.enums.Role;
import com.apnapg.exceptions.ConflictException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.mappers.OwnerMapper;
import com.apnapg.repository.OwnerRepository;
import com.apnapg.repository.UserRepository;
import com.apnapg.service.FileStorageService;
import com.apnapg.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class OwnerServiceImpl implements OwnerService {
//
//    private final OwnerRepository ownerRepository;
//    private final UserRepository userRepository;
//    private final OwnerMapper ownerMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final FileStorageService fileStorageService;
//
//    // ==========================================
//    // REGISTER OWNER
//    // ==========================================
//    @Override
//    public OwnerResponseDTO registerOwner(OwnerRegistrationDTO dto) {
//
//        if (userRepository.existsByEmail(dto.email()))
//            throw new ConflictException("Email already registered");
//
//        if (ownerRepository.existsByPhoneNumber(dto.phoneNumber()))
//            throw new ConflictException("Phone number already in use");
//
//        // 1️⃣ Create User
//        User user = ownerMapper.toUserEntity(dto);
//        user.setPassword(passwordEncoder.encode(dto.password()));
//        user.setRole(Role.OWNER);
//        user.setAuthProvider(AuthProvider.LOCAL);
//
//        userRepository.save(user);
//
//        // 2️⃣ Create Owner
//        Owner owner = ownerMapper.toEntity(dto);
//        owner.setUser(user);
//
//        ownerRepository.save(owner);
//
//        log.info("Owner registered successfully: {}", dto.email());
//
//        return ownerMapper.toResponseDTO(owner);
//    }
//
//    // ==========================================
//    // GET PROFILE
//    // ==========================================
//    @Override
//    @Transactional(readOnly = true)
//    public OwnerResponseDTO getOwnerProfile(Long ownerId) {
//
//        Owner owner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        return ownerMapper.toResponseDTO(owner);
//    }
//
//    // ==========================================
//    // UPDATE OWNER
//    // ==========================================
//    @Override
//    public OwnerResponseDTO updateOwner(Long ownerId, OwnerUpdateDTO dto) {
//
//        Owner owner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        ownerMapper.updateEntity(dto, owner);
//
//        log.info("Owner {} updated profile", ownerId);
//
//        return ownerMapper.toResponseDTO(owner);
//    }
//
//    // ==========================================
//    // GET OWNER PG IDs
//    // ==========================================
//    @Override
//    @Transactional(readOnly = true)
//    public List<Long> getOwnerPGs(Long ownerId) {
//
//        Owner owner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        return owner.getPgs()
//                .stream()
//                .map(pg -> pg.getId())
//                .toList();
//    }
//
//    // ==========================================
//    // UPLOAD PROFILE IMAGE
//    // ==========================================
//    @Override
//    public String uploadProfileImage(Long ownerId, String imageUrl) {
//
//        Owner owner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        if (owner.getProfileImageUrl() != null) {
//            fileStorageService.delete(owner.getProfileImageUrl());
//        }
//
//        owner.setProfileImageUrl(imageUrl);
//
//        log.info("Owner {} updated profile image", ownerId);
//
//        return imageUrl;
//    }
//}

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    // ==========================================
    // REGISTER OWNER
    // ==========================================
    @Override
    public OwnerResponseDTO registerOwner(OwnerRegistrationDTO dto) {

        if (userRepository.existsByEmail(dto.email()))
            throw new ConflictException("Email already registered");

        if (ownerRepository.existsByPhoneNumber(dto.phoneNumber()))
            throw new ConflictException("Phone number already in use");

        // 1️⃣ Create User
        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.OWNER)
                .enabled(true)
                .accountNonLocked(true)
                .failedLoginAttempts(0)
                .authProvider(AuthProvider.LOCAL)
                .build();

        userRepository.save(user);

        // 2️⃣ Create Owner
        Owner owner = Owner.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber())
                .address(dto.address())
                .businessName(dto.businessName())
                .gstNumber(dto.gstNumber())
                .profileImageUrl(null)
                .user(user)
                .build();

        ownerRepository.save(owner);

        log.info("Owner registered successfully: {}", dto.email());

        return toResponseDTO(owner);
    }

    // ==========================================
    // GET PROFILE
    // ==========================================
    @Override
    @Transactional(readOnly = true)
    public OwnerResponseDTO getOwnerProfile(Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        return toResponseDTO(owner);
    }

    // ==========================================
    // UPDATE OWNER
    // ==========================================
    @Override
    public OwnerResponseDTO updateOwner(Long ownerId, OwnerUpdateDTO dto) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        if (dto.firstName() != null)
            owner.setFirstName(dto.firstName());

        if (dto.lastName() != null)
            owner.setLastName(dto.lastName());

        if (dto.phoneNumber() != null)
            owner.setPhoneNumber(dto.phoneNumber());

        if (dto.address() != null)
            owner.setAddress(dto.address());

        if (dto.businessName() != null)
            owner.setBusinessName(dto.businessName());

        if (dto.gstNumber() != null)
            owner.setGstNumber(dto.gstNumber());

        log.info("Owner {} updated profile", ownerId);

        return toResponseDTO(owner);
    }

    // ==========================================
    // GET OWNER PG IDs
    // ==========================================
    @Override
    @Transactional(readOnly = true)
    public List<Long> getOwnerPGs(Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        return owner.getPgs()
                .stream()
                .map(PG::getId)
                .toList();
    }

    // ==========================================
    // UPLOAD PROFILE IMAGE
    // ==========================================
    @Override
    public String uploadProfileImage(Long ownerId, String imageUrl) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        if (owner.getProfileImageUrl() != null) {
            fileStorageService.delete(owner.getProfileImageUrl());
        }

        owner.setProfileImageUrl(imageUrl);

        log.info("Owner {} updated profile image", ownerId);

        return imageUrl;
    }

    // ==========================================
    // 🔹 MANUAL MAPPER
    // ==========================================
    private OwnerResponseDTO toResponseDTO(Owner owner) {

        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getUser().getEmail(),
                owner.getPhoneNumber(),
                owner.getBusinessName(),
                owner.getProfileImageUrl()
        );
    }
}
