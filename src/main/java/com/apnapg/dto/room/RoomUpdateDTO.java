package com.apnapg.dto.room;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomUpdateDTO(

        @NotBlank(message = "Room number is required")
        String roomNumber,

        @Positive(message = "Total beds must be greater than 0")
        @Max(value = 10, message = "Maximum 10 beds allowed")
        Integer totalBeds
) {}
