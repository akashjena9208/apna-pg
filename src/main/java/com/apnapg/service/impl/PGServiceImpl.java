package com.apnapg.service.impl;
import com.apnapg.dto.pagination.PageResponseDTO;
import com.apnapg.dto.pg.PGCreateRequest;
import com.apnapg.dto.pg.PGResponse;
import com.apnapg.dto.pg.PGSearchDTO;
import com.apnapg.entity.Owner;
import com.apnapg.entity.PG;
import com.apnapg.exceptions.ConflictException;
import com.apnapg.exceptions.ForbiddenOperationException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.mappers.PGMapper;
import com.apnapg.repository.OwnerRepository;
import com.apnapg.repository.PGRepository;
import com.apnapg.service.PGService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class PGServiceImpl implements PGService {
//
//    private final PGRepository pgRepository;
//    private final OwnerRepository ownerRepository;
//
//    // ======================================================
//    // CREATE PG (Owner Scoped)
//    // ======================================================
//    @Override
//    public PGResponse createPG(PGCreateRequest request, Long ownerId) {
//
//        Owner owner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        if (pgRepository.existsByNameIgnoreCaseAndCityIgnoreCase(
//                request.name(), request.city())) {
//            throw new ConflictException("PG already exists in this city");
//        }
//
//        PG pg = PG.builder()
//                .name(request.name())
//                .address(request.address())
//                .city(request.city())
//                .contactNumber(request.contactNumber())
//                .rentPerMonth(request.rentPerMonth())
//                .totalRooms(request.totalRooms())
//                .owner(owner)
//                .build();
//
//        pgRepository.save(pg);
//
//        log.info("PG '{}' created by owner {}", pg.getName(), ownerId);
//
//        return toResponse(pg);
//    }
//
//    // ======================================================
//    // UPDATE PG (Owner Scoped)
//    // ======================================================
//    @Override
//    public PGResponse updatePG(Long pgId, PGCreateRequest request, Long ownerId) {
//
//        PG pg = getOwnerPG(pgId, ownerId);
//
//        pg.setName(request.name());
//        pg.setAddress(request.address());
//        pg.setCity(request.city());
//        pg.setContactNumber(request.contactNumber());
//        pg.setRentPerMonth(request.rentPerMonth());
//        pg.setTotalRooms(request.totalRooms());
//
//        log.info("PG {} updated by owner {}", pgId, ownerId);
//
//        return toResponse(pg);
//    }
//
//    // ======================================================
//    // GET SINGLE PG
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public PGResponse getPG(Long pgId) {
//
//        PG pg = pgRepository.findById(pgId)
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));
//
//        return toResponse(pg);
//    }
//
//    // ======================================================
//    // DELETE PG (Owner Scoped + Safety)
//    // ======================================================
//    @Override
//    public void deletePG(Long pgId, Long ownerId) {
//
//        PG pg = getOwnerPG(pgId, ownerId);
//
//        boolean hasOccupiedRooms = pg.getRooms()
//                .stream()
//                .anyMatch(room ->
//                        room.getTenants() != null &&
//                                !room.getTenants().isEmpty()
//                );
//
//        if (hasOccupiedRooms) {
//            throw new ConflictException("Cannot delete PG with occupied rooms");
//        }
//
//        pgRepository.delete(pg);
//
//        log.info("PG {} deleted by owner {}", pgId, ownerId);
//    }
//
//    // ======================================================
//    // SEARCH PGs WITH REAL PAGINATION
//    // ======================================================
//    @Override
//    @Transactional(readOnly = true)
//    public PageResponseDTO<PGResponse> searchPGs(
//            PGSearchDTO search,
//            int page,
//            int size
//    ) {
//
//        Pageable pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by("createdAt").descending()
//        );
//
//        Page<PG> result;
//
//        if (search.city() != null &&
//                search.minRent() != null &&
//                search.maxRent() != null) {
//
//            result = pgRepository
//                    .findByCityIgnoreCaseAndRentPerMonthBetween(
//                            search.city(),
//                            search.minRent(),
//                            search.maxRent(),
//                            pageable
//                    );
//
//        } else if (search.city() != null) {
//
//            result = pgRepository
//                    .findByCityIgnoreCase(search.city(), pageable);
//
//        } else {
//            result = pgRepository.findAll(pageable);
//        }
//
//        return new PageResponseDTO<>(
//                result.map(this::toResponse).getContent(),
//                result.getNumber(),
//                result.getSize(),
//                result.getTotalElements(),
//                result.getTotalPages(),
//                result.isLast()
//        );
//    }
//
//    // ======================================================
//    // INTERNAL OWNER SECURITY CHECK
//    // ======================================================
//    private PG getOwnerPG(Long pgId, Long ownerId) {
//
//        PG pg = pgRepository.findById(pgId)
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));
//
//        if (!pg.getOwner().getId().equals(ownerId)) {
//            throw new ForbiddenOperationException(
//                    "You do not own this PG"
//            );
//        }
//
//        return pg;
//    }
//
//    // ======================================================
//    // 🔹 MANUAL MAPPER
//    // ======================================================
//    private PGResponse toResponse(PG pg) {
//
//        int availableRooms = (int) pg.getRooms()
//                .stream()
//                .filter(room -> room.getAvailableBeds() > 0)
//                .count();
//        return new PGResponse(
//                pg.getId(),
//                pg.getName(),
//                pg.getAddress(),
//                pg.getCity(),
//                pg.getContactNumber(),
//                pg.getRentPerMonth(),
//                pg.getTotalRooms(),
//                availableRooms,
//                pg.getImageUrls(),
//                pg.getOwner().getId()
//        );
//
////        return new PGResponse(
////                pg.getId(),
////                pg.getName(),
////                pg.getAddress(),
////                pg.getCity(),
////                pg.getContactNumber(),
////                pg.getRentPerMonth(),
////                pg.getTotalRooms(),
////                pg.getVersion(),
////                availableRooms,
////                pg.getImageUrls(),
////                pg.getOwner().getId()
////        );
//    }
//}

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PGServiceImpl implements PGService {

    private final PGRepository pgRepository;
    private final OwnerRepository ownerRepository;

    // ======================================================
    // CREATE PG (Owner Scoped)
    // ======================================================
    @Override
    public PGResponse createPG(PGCreateRequest request, Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        if (pgRepository.existsByNameIgnoreCaseAndCityIgnoreCase(
                request.name(), request.city())) {
            throw new ConflictException("PG already exists in this city");
        }

        PG pg = PG.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .contactNumber(request.contactNumber())
                .rentPerMonth(request.rentPerMonth())
                .totalRooms(request.totalRooms())
                .owner(owner)
                .build();

        pgRepository.save(pg);

        log.info("PG '{}' created by owner {}", pg.getName(), ownerId);

        return toResponse(pg);
    }

    // ======================================================
    // UPDATE PG (Owner Scoped)
    // ======================================================
    @Override
    public PGResponse updatePG(Long pgId, PGCreateRequest request, Long ownerId) {

        PG pg = getOwnerPG(pgId, ownerId);

        pg.setName(request.name());
        pg.setAddress(request.address());
        pg.setCity(request.city());
        pg.setContactNumber(request.contactNumber());
        pg.setRentPerMonth(request.rentPerMonth());
        pg.setTotalRooms(request.totalRooms());

        log.info("PG {} updated by owner {}", pgId, ownerId);

        return toResponse(pg);
    }

    // ======================================================
    // GET SINGLE PG
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public PGResponse getPG(Long pgId) {

        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));

        return toResponse(pg);
    }

    // ======================================================
    // DELETE PG (Owner Scoped + Safety)
    // ======================================================
    @Override
    public void deletePG(Long pgId, Long ownerId) {

        PG pg = getOwnerPG(pgId, ownerId);

        // 🔥 SAFE NULL CHECK
        boolean hasOccupiedRooms = pg.getRooms() != null &&
                pg.getRooms().stream()
                        .anyMatch(room ->
                                room.getTenants() != null &&
                                        !room.getTenants().isEmpty()
                        );

        if (hasOccupiedRooms) {
            throw new ConflictException("Cannot delete PG with occupied rooms");
        }

        pgRepository.delete(pg);

        log.info("PG {} deleted by owner {}", pgId, ownerId);
    }

    // ======================================================
    // SEARCH PGs WITH REAL PAGINATION
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<PGResponse> searchPGs(
            PGSearchDTO search,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<PG> result;

        String city = search.city();
        BigDecimal min = search.minRent();
        BigDecimal max = search.maxRent();

        boolean hasCity = city != null && !city.isBlank();
        boolean hasMin = min != null;
        boolean hasMax = max != null;

        if (hasCity && hasMin && hasMax) {

            result = pgRepository
                    .findByCityIgnoreCaseAndRentPerMonthBetween(
                            city.trim(),
                            min,
                            max,
                            pageable
                    );

        } else if (hasCity) {

            result = pgRepository
                    .findByCityIgnoreCase(city.trim(), pageable);

        } else {

            result = pgRepository.findAll(pageable);
        }

        return new PageResponseDTO<>(
                result.map(this::toResponse).getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

//    @Override
//    @Transactional(readOnly = true)
//    public PageResponseDTO<PGResponse> searchPGs(
//            PGSearchDTO search,
//            int page,
//            int size
//    ) {
//
//        Pageable pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by("createdAt").descending()
//        );
//
//        Page<PG> result;
//
//        if (search.city() != null &&
//                search.minRent() != null &&
//                search.maxRent() != null) {
//
//            result = pgRepository
//                    .findByCityIgnoreCaseAndRentPerMonthBetween(
//                            search.city(),
//                            search.minRent(),
//                            search.maxRent(),
//                            pageable
//                    );
//
//        } else if (search.city() != null) {
//
//            result = pgRepository
//                    .findByCityIgnoreCase(search.city(), pageable);
//
//        } else {
//            result = pgRepository.findAll(pageable);
//        }
//
//        return new PageResponseDTO<>(
//                result.map(this::toResponse).getContent(),
//                result.getNumber(),
//                result.getSize(),
//                result.getTotalElements(),
//                result.getTotalPages(),
//                result.isLast()
//        );
//    }

    // ======================================================
    // INTERNAL OWNER SECURITY CHECK
    // ======================================================
    private PG getOwnerPG(Long pgId, Long ownerId) {

        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));

        if (!pg.getOwner().getId().equals(ownerId)) {
            throw new ForbiddenOperationException(
                    "You do not own this PG"
            );
        }

        return pg;
    }


    @Override
    public void addImageToPG(Long pgId, Long ownerId, String fileName) {

        PG pg = getOwnerPG(pgId, ownerId);

        if (pg.getImageUrls() == null) {
            pg.setImageUrls(new ArrayList<>());
        }

        pg.getImageUrls().add(fileName);

        log.info("Image added to PG {}", pgId);
    }


    // ======================================================
    // 🔹 MANUAL MAPPER (NULL SAFE)
    // ======================================================
    private PGResponse toResponse(PG pg) {

        int availableRooms = 0;

        if (pg.getRooms() != null) {
            availableRooms = (int) pg.getRooms()
                    .stream()
                    .filter(room -> room.getAvailableBeds() != null &&
                            room.getAvailableBeds() > 0)
                    .count();
        }

        return new PGResponse(
                pg.getId(),
                pg.getName(),
                pg.getAddress(),
                pg.getCity(),
                pg.getContactNumber(),
                pg.getRentPerMonth(),
                pg.getTotalRooms(),
                availableRooms,
                pg.getImageUrls(),
                pg.getOwner().getId()
        );
    }
}
