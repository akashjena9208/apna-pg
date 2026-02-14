package com.apnapg.dto.room;

public record RoomResponseDTO(
        Long id,
        String roomNumber,
        Integer totalBeds,
        Integer availableBeds,
        Integer occupiedBeds
) {}
