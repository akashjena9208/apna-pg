package com.apnapg.service;

import com.apnapg.dto.PGCreateRequest;
import com.apnapg.entity.Owner;
import com.apnapg.entity.PG;
import com.apnapg.mapper.PGMapper;
import com.apnapg.repositories.OwnerRepository;
import com.apnapg.repositories.PGRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PGService {

    private final PGRepository pgRepository;
    private final OwnerRepository ownerRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public PG createPG(Long ownerId, PGCreateRequest dto, List<MultipartFile> images) throws Exception {
        log.info("Creating PG '{}' for owner {}", dto.name(), ownerId);

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id " + ownerId));

        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String url = fileStorageService.saveFile(image.getBytes(), image.getOriginalFilename());
                imageUrls.add(url);
            }
        }

        PG pg = PGMapper.toEntity(dto, imageUrls, owner);
        PG savedPG = pgRepository.save(pg);

        log.info("PG '{}' created successfully with id {}", savedPG.getName(), savedPG.getId());
        return savedPG;
    }

    public List<PG> searchPGs(String city, int rentMax) {
        return pgRepository.findByCityContainingIgnoreCaseAndRentPerMonthLessThanEqual(city, rentMax);
    }


    public List<PG> getPGsByOwner(Long ownerId) {
        return pgRepository.findByOwnerId(ownerId);
    }
}
