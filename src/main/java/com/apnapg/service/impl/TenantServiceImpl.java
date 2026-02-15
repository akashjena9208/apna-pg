package com.apnapg.service.impl;

import com.apnapg.dto.tenant.*;
import com.apnapg.entity.Owner;
import com.apnapg.entity.Room;
import com.apnapg.entity.Tenant;
import com.apnapg.entity.User;
import com.apnapg.enums.AuthProvider;
import com.apnapg.enums.Role;
import com.apnapg.exceptions.*;
import com.apnapg.repository.UserRepository;
import com.apnapg.repository.RoomRepository;
import com.apnapg.repository.TenantRepository;
import com.apnapg.security.SecurityUtils;
import com.apnapg.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class TenantServiceImpl implements TenantService {
//
//    private final TenantRepository tenantRepository;
//    private final RoomRepository roomRepository;
//    private final UserRepository userRepository;
//    private final TenantMapper tenantMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    // ==========================================
//    // REGISTER TENANT
//    // ==========================================
//    @Override
//    public TenantResponseDTO registerTenant(TenantRegistrationDTO dto, String aadhaarUrl) {
//
//        if (aadhaarUrl == null || aadhaarUrl.isBlank())
//            throw new BadRequestException("Aadhaar document is required");
//
//        if (userRepository.existsByEmail(dto.email()))
//            throw new ConflictException("Email already registered");
//
//        // 1️⃣ Create User
//        User user = tenantMapper.toUserEntity(dto);
//        user.setPassword(passwordEncoder.encode(dto.password()));
//        user.setRole(Role.TENANT);
//
//        userRepository.save(user);
//
//        // 2️⃣ Create Tenant
//        Tenant tenant = tenantMapper.toTenantEntity(dto);
//        tenant.setAadhaarUrl(aadhaarUrl);
//        tenant.setUser(user);
//
//        tenantRepository.save(tenant);
//
//        log.info("Tenant registered successfully: {}", dto.email());
//
//        return tenantMapper.toResponseDTO(tenant);
//    }
//
//    // ==========================================
//    // GET PROFILE
//    // ==========================================
//    @Override
//    @Transactional(readOnly = true)
//    public TenantResponseDTO getTenantProfile(Long tenantId) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        return tenantMapper.toResponseDTO(tenant);
//    }
//
//    // ==========================================
//    // UPDATE PROFILE
//    // ==========================================
//    @Override
//    public TenantResponseDTO updateTenant(Long tenantId, TenantUpdateDTO dto) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        tenantMapper.updateTenantFromDTO(dto, tenant);
//
//        log.info("Tenant {} profile updated", tenantId);
//
//        return tenantMapper.toResponseDTO(tenant);
//    }
//
//    // ==========================================
//    // ALLOCATE ROOM
//    // ==========================================
//    @Override
//    public void allocateRoom(Long tenantId, Long roomId) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        if (tenant.getRoom() != null)
//            throw new ConflictException("Tenant already has a room");
//
//        Room room = roomRepository.findByIdForUpdate(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        if (room.getAvailableBeds() <= 0)
//            throw new ConflictException("Room is full");
//
//        room.setAvailableBeds(room.getAvailableBeds() - 1);
//        tenant.setRoom(room);
//
//        log.info("Room {} allocated to tenant {}", roomId, tenantId);
//    }
//
//    // ==========================================
//    // VACATE ROOM
//    // ==========================================
//    @Override
//    public void vacateRoom(Long tenantId) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        Room room = tenant.getRoom();
//
//        if (room == null)
//            throw new BadRequestException("Tenant has no allocated room");
//
//        room.setAvailableBeds(room.getAvailableBeds() + 1);
//        tenant.setRoom(null);
//
//        log.info("Tenant {} vacated room {}", tenantId, room.getId());
//    }
//}


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageServiceImpl fileStorageService;

    @Override
    public TenantResponseDTO registerTenant(
            TenantRegistrationDTO dto,
            MultipartFile aadhaarFile
    ) {

        // 🔥 Store file
        String aadhaarFileName = fileStorageService.store(aadhaarFile);

        // 🔥 Create User + Tenant
        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.TENANT)
                .enabled(true)
                .accountNonLocked(true)
                .authProvider(AuthProvider.LOCAL)
                .build();

        userRepository.save(user);

        Tenant tenant = Tenant.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber())
                .gender(dto.gender())
                .occupation(dto.occupation())
                .dateOfBirth(dto.dateOfBirth())
                .address(dto.address())
                .emergencyContactName(dto.emergencyContactName())
                .emergencyContactNumber(dto.emergencyContactNumber())
                .aadhaarUrl(aadhaarFileName)
                .user(user)
                .build();

        tenantRepository.save(tenant);

        return new TenantResponseDTO(
                tenant.getId(),
                tenant.getFirstName(),
                tenant.getLastName(),
                user.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAadhaarUrl(),
                null
        );
    }

//
//    @Override
//    public TenantResponseDTO registerTenant(
//            TenantRegistrationDTO dto,
//            String aadhaarUrl
//    ) {
//
//        if (aadhaarUrl == null || aadhaarUrl.isBlank())
//            throw new BadRequestException("Aadhaar document is required");
//
//        String normalizedEmail = dto.email().trim().toLowerCase();
//
//        // 🔎 Check existing user (case insensitive)
//        if (userRepository.existsByEmail(normalizedEmail)) {
//            throw new ConflictException("Email already registered");
//        }
//
//        // 1️⃣ Create User
//        User user = User.builder()
//                .email(normalizedEmail)
//                .password(passwordEncoder.encode(dto.password()))
//                .role(Role.TENANT)
//                .enabled(true)
//                .accountNonLocked(true)
//                .failedLoginAttempts(0)
//                .build();
//
//        user = userRepository.save(user);
//
//        // 2️⃣ Create Tenant
//        Tenant tenant = Tenant.builder()
//                .firstName(dto.firstName())
//                .lastName(dto.lastName())
//                .phoneNumber(dto.phoneNumber())
//                .aadhaarUrl(aadhaarUrl)
//                .gender(dto.gender())
//                .occupation(dto.occupation())
//                .dateOfBirth(dto.dateOfBirth())
//                .address(dto.address())
//                .emergencyContactName(dto.emergencyContactName())
//                .emergencyContactNumber(dto.emergencyContactNumber())
//                .user(user)
//                .build();
//
//        tenantRepository.save(tenant);
//
//        log.info("Tenant registered successfully: {}", normalizedEmail);
//
//        return toResponseDTO(tenant);
//    }


    // ==========================================
    // GET PROFILE
    // ==========================================
    @Override
    @Transactional(readOnly = true)
    public TenantResponseDTO getTenantProfile(Long tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        return toResponseDTO(tenant);
    }

    // ==========================================
    // UPDATE PROFILE
    // ==========================================
    @Override
    public TenantResponseDTO updateTenant(Long tenantId, TenantUpdateDTO dto) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        if (dto.firstName() != null)
            tenant.setFirstName(dto.firstName());

        if (dto.lastName() != null)
            tenant.setLastName(dto.lastName());

        if (dto.phoneNumber() != null)
            tenant.setPhoneNumber(dto.phoneNumber());

        if (dto.gender() != null)
            tenant.setGender(dto.gender());

        if (dto.occupation() != null)
            tenant.setOccupation(dto.occupation());

        if (dto.dateOfBirth() != null)
            tenant.setDateOfBirth(dto.dateOfBirth());

        if (dto.address() != null)
            tenant.setAddress(dto.address());

        if (dto.emergencyContactName() != null)
            tenant.setEmergencyContactName(dto.emergencyContactName());

        if (dto.emergencyContactNumber() != null)
            tenant.setEmergencyContactNumber(dto.emergencyContactNumber());

        log.info("Tenant {} profile updated", tenantId);

        return toResponseDTO(tenant);
    }

    // ==========================================
    // ALLOCATE ROOM
    // ==========================================
//    @Override
//    public void allocateRoom(Long tenantId, Long roomId) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        if (tenant.getRoom() != null)
//            throw new ConflictException("Tenant already has a room");
//
//        Room room = roomRepository.findByIdForUpdate(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        if (room.getAvailableBeds() <= 0)
//            throw new ConflictException("Room is full");
//
//        room.setAvailableBeds(room.getAvailableBeds() - 1);
//        tenant.setRoom(room);
//
//        log.info("Room {} allocated to tenant {}", roomId, tenantId);
//    }

//    @Override
//    public void allocateRoom(Long tenantId, Long roomId) {
//
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        if (tenant.getRoom() != null)
//            throw new ConflictException("Tenant already has a room");
//
//        Room room = roomRepository.findByIdForUpdate(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        Long currentOwnerId = SecurityUtils.getCurrentUserId();
//
//        if (!room.getPg().getOwner().getUser().getId().equals(currentOwnerId)) {
//            throw new ForbiddenOperationException("You do not own this PG");
//        }
//
//        if (room.getAvailableBeds() <= 0)
//            throw new ConflictException("Room is full");
//
//        room.setAvailableBeds(room.getAvailableBeds() - 1);
//        tenant.setRoom(room);
//    }

    @Override
    public void allocateRoom(Long tenantId, Long roomId, Long ownerId) {

        // 1️⃣ Get Tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tenant not found"));

        // 2️⃣ Prevent double allocation
        if (tenant.getRoom() != null) {
            throw new ConflictException("Tenant already has a room");
        }

        // 3️⃣ Lock room row (prevents race condition)
        Room room = roomRepository.findByIdForUpdate(roomId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found"));

        // 4️⃣ Ownership validation (VERY IMPORTANT)
        if (!room.getPg().getOwner().getId().equals(ownerId)) {
            throw new ForbiddenOperationException("You do not own this PG");
        }

        // 5️⃣ Check availability
        if (room.getAvailableBeds() == null || room.getAvailableBeds() <= 0) {
            throw new ConflictException("Room is full");
        }

        // 6️⃣ Allocate
        room.setAvailableBeds(room.getAvailableBeds() - 1);
        tenant.setRoom(room);

        log.info("Room {} allocated to tenant {} by owner {}",
                roomId, tenantId, ownerId);
    }




    // ==========================================
    // VACATE ROOM
    // ==========================================
    @Override
    public void vacateRoom(Long tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        Room room = tenant.getRoom();

        if (room == null)
            throw new BadRequestException("Tenant has no allocated room");

        room.setAvailableBeds(room.getAvailableBeds() + 1);
        tenant.setRoom(null);

        log.info("Tenant {} vacated room {}", tenantId, room.getId());
    }

    // ==========================================
    // 🔹 MANUAL MAPPER
    // ==========================================
    private TenantResponseDTO toResponseDTO(Tenant tenant) {

        return new TenantResponseDTO(
                tenant.getId(),
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getUser().getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAadhaarUrl(),
                tenant.getRoom() != null
                        ? tenant.getRoom().getId()
                        : null
        );
    }
}
