package com.apnapg.dto;



public record RoomAvailabilityDTO(
        Long pgId,
        String pgName,
        Long roomId,
        String roomNumber,
        Integer totalBeds,
        Integer availableBeds
) {}

