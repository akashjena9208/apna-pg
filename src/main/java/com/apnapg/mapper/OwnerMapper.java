package com.apnapg.mapper;

import com.apnapg.dto.OwnerRegistrationDTO;
import com.apnapg.dto.OwnerResponseDTO;
import com.apnapg.entity.Owner;
import com.apnapg.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OwnerMapper {

    public Owner toEntity(OwnerRegistrationDTO dto, String profileImageUrl, User user) {
        return Owner.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .address(dto.address())
                .businessName(dto.businessName())
                .gstNumber(dto.gstNumber())
                .profileImageUrl(profileImageUrl)
                .user(user)
                .build();
    }

    public OwnerResponseDTO toResponseDTO(Owner owner) {
        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getPhoneNumber(),
                owner.getBusinessName(),
                owner.getProfileImageUrl()
        );
    }
}
