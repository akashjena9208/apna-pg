package com.apnapg.dto.room;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record RoomAvailabilityDTO(

        Long pgId,
        String pgName,
        Long roomId,
        String roomNumber,
        @Min(1)
        Integer totalBeds,
        @PositiveOrZero
        Integer availableBeds,
        @PositiveOrZero
        Integer occupiedBeds,
        Double occupancyPercentage   // calculated field (not from DB)

) {}