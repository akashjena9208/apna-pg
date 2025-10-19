package com.apnapg.dto;

public record RoomResponseDTO(
        Long id,
        String roomNumber,
        Integer totalBeds,
        Integer availableBeds
) {}
