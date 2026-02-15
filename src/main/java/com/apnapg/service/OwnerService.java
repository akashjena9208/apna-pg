package com.apnapg.service;

import com.apnapg.dto.owner.*;

import java.util.List;

public interface OwnerService {

    OwnerResponseDTO registerOwner(OwnerRegistrationDTO dto);

    OwnerResponseDTO getOwnerProfile(Long ownerId);

    OwnerResponseDTO updateOwner(Long ownerId, OwnerUpdateDTO dto);

    List<Long> getOwnerPGs(Long ownerId);

    String uploadProfileImage(Long ownerId, String imageUrl);
}
