package com.apnapg.controllers;

import com.apnapg.dto.TenantRegistrationDTO;
import com.apnapg.dto.TenantResponseDTO;
import com.apnapg.entity.Tenant;
import com.apnapg.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
@Slf4j
public class TenantController {

    private final TenantService tenantService;


//    @PostMapping("/register")
//    public ResponseEntity<TenantResponseDTO> registerTenant(
//            @RequestPart("tenant") @Valid TenantRegistrationDTO tenantDTO,
//            @RequestPart("aadhaarFile") MultipartFile aadhaarFile
//    ) throws Exception {
//
//        log.info("Received registration request for email {}", tenantDTO.email());
//
//        byte[] bytes = aadhaarFile.getBytes();
//        String fileName = aadhaarFile.getOriginalFilename();
//
//        TenantResponseDTO response = tenantService.registerTenant(tenantDTO, bytes, fileName);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/register")
    public ResponseEntity<TenantResponseDTO> registerTenant(
            @RequestPart("tenant") @Valid TenantRegistrationDTO tenantDTO,
            @RequestPart("aadhaarFile") MultipartFile aadhaarFile
    ) throws Exception {

        log.info("Received registration request for email {}", tenantDTO.email());

        byte[] bytes = aadhaarFile.getBytes();
        String fileName = aadhaarFile.getOriginalFilename();

        Tenant tenant = tenantService.registerTenant(tenantDTO, bytes, fileName);

        TenantResponseDTO response = new TenantResponseDTO(
                tenant.getId(),
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAadhaarUrl()
        );

        return ResponseEntity.ok(response);
    }
}
