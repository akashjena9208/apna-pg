package com.apnapg.mapper;

import com.apnapg.dto.TenantRegistrationDTO;
import com.apnapg.entity.Tenant;
import com.apnapg.entity.User;

public class TenantMapper {

    public static Tenant toEntity(TenantRegistrationDTO dto, String aadhaarUrl, User user) {
        return Tenant.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .aadhaarUrl(aadhaarUrl)
                .gender(dto.gender())
                .occupation(dto.occupation())
                .dateOfBirth(dto.dateOfBirth())
                .address(dto.address())
                .emergencyContactName(dto.emergencyContactName())
                .emergencyContactNumber(dto.emergencyContactNumber())
                .user(user)
                .build();
    }
}
