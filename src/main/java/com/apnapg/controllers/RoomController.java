package com.apnapg.controllers;

import com.apnapg.dto.api.ApiResponse;
import com.apnapg.dto.room.*;
import com.apnapg.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // OWNER ONLY
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{pgId}")
    public ApiResponse<RoomResponseDTO> createRoom(
            @PathVariable Long pgId,
            @Valid @RequestBody RoomCreateDTO dto) {

        return ApiResponse.success(
                roomService.createRoom(dto, pgId),
                "Room created successfully"
        );
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{roomId}")
    public ApiResponse<RoomResponseDTO> updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomUpdateDTO dto) {

        return ApiResponse.success(
                roomService.updateRoom(roomId, dto),
                "Room updated successfully"
        );
    }

    // PUBLIC
    @GetMapping("/availability/{pgId}")
    public ApiResponse<List<RoomAvailabilityDTO>> getAvailability(
            @PathVariable Long pgId) {

        return ApiResponse.success(
                roomService.getRoomAvailability(pgId),
                "Room availability fetched"
        );
    }
}
