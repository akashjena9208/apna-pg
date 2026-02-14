package com.apnapg.dto.room;

public record RoomAllocationResponseDTO(
        Long tenantId,
        Long roomId,
        Integer availableBeds
) {}
