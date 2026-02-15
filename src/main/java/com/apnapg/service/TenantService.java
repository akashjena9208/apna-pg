package com.apnapg.service;

import com.apnapg.dto.tenant.*;
import org.springframework.web.multipart.MultipartFile;

public interface TenantService {

    //TenantResponseDTO registerTenant(TenantRegistrationDTO dto, String aadhaarUrl);
    TenantResponseDTO registerTenant(
            TenantRegistrationDTO dto,
            MultipartFile aadhaarFile
    );


    TenantResponseDTO getTenantProfile(Long tenantId);

    //TenantResponseDTO updateTenant(Long tenantId, TenantRegistrationDTO dto);
    TenantResponseDTO updateTenant(Long tenantId, TenantUpdateDTO dto);


//    void allocateRoom(Long tenantId, Long roomId);
        void allocateRoom(Long tenantId, Long roomId, Long ownerId);



    void vacateRoom(Long tenantId);
}
