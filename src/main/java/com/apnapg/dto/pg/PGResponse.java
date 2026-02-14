package com.apnapg.dto.pg;


import java.math.BigDecimal;
import java.util.List;

public record PGResponse(

        Long id,
        String name,
        String address,
        String city,
        String contactNumber,
        BigDecimal rentPerMonth,
        Integer totalRooms,
        // Long version,
        Integer availableRooms,

        List<String> imageUrls,

        Long ownerId
) {}


