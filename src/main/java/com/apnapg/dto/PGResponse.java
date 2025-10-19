package com.apnapg.dto;

import java.util.List;

public record PGResponse(
        Long id,
        String name,
        String address,
        String city,
        String contactNumber,
        Double rentPerMonth,
        Integer totalRooms,
        List<String> imageUrls
) {}
