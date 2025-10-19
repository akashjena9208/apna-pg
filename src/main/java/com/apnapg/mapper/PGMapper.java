package com.apnapg.mapper;

import com.apnapg.dto.PGCreateRequest;
import com.apnapg.dto.PGResponse;
import com.apnapg.entity.Owner;
import com.apnapg.entity.PG;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PGMapper {

    public PG toEntity(PGCreateRequest dto, List<String> imageUrls, Owner owner) {
        return PG.builder()
                .name(dto.name())
                .address(dto.address())
                .city(dto.city())
                .contactNumber(dto.contactNumber())
                .rentPerMonth(dto.rentPerMonth())
                .totalRooms(dto.totalRooms())
                .imageUrls(imageUrls)
                .owner(owner)
                .build();
    }

    public PGResponse toResponse(PG pg) {
        return new PGResponse(
                pg.getId(),
                pg.getName(),
                pg.getAddress(),
                pg.getCity(),
                pg.getContactNumber(),
                pg.getRentPerMonth(),
                pg.getTotalRooms(),
                pg.getImageUrls()
        );
    }
}
