package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.owner.OwnerRegistrationDTO;
import com.apnapg.dto.owner.OwnerResponseDTO;
import com.apnapg.dto.owner.OwnerUpdateDTO;
import com.apnapg.entity.Owner;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface OwnerMapper {

    // =========================================
    // REGISTER → ENTITY
    // =========================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pgs", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Owner toEntity(OwnerRegistrationDTO dto);

    // =========================================
    // ENTITY → RESPONSE
    // =========================================
    @Mapping(target = "email", source = "user.email")
    OwnerResponseDTO toResponseDTO(Owner owner);

    // =========================================
    // UPDATE
    // =========================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImageUrl", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pgs", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(OwnerUpdateDTO dto, @MappingTarget Owner owner);
}
