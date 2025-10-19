package com.apnapg.dto;

import jakarta.validation.constraints.*;

public record RoomCreateDTO(
        @NotBlank String roomNumber,
        @NotNull Integer totalBeds
) {}
