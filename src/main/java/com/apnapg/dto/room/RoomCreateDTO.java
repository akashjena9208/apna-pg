package com.apnapg.dto.room;

import jakarta.validation.constraints.*;

public record RoomCreateDTO(
        @NotBlank String roomNumber,
        @NotNull @Positive @Max(10) Integer totalBeds
) {}
