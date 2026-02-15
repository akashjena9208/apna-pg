package com.apnapg.service.impl;

import com.apnapg.dto.room.*;
import com.apnapg.entity.PG;
import com.apnapg.entity.Room;
import com.apnapg.exceptions.ConflictException;
import com.apnapg.exceptions.ForbiddenOperationException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.repository.PGRepository;
import com.apnapg.repository.RoomRepository;
import com.apnapg.security.SecurityUtils;
import com.apnapg.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class RoomServiceImpl implements RoomService {
//
//    private final RoomRepository roomRepository;
//    private final PGRepository pgRepository;
//    private final RoomMapper roomMapper;
//
//    @Override
//    public RoomResponseDTO createRoom(RoomCreateDTO dto, Long pgId) {
//
//        PG pg = pgRepository.findById(pgId)
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));
//
//        Long ownerUserId = SecurityUtils.getCurrentUserId();
//
//        if (!pg.getOwner().getUser().getId().equals(ownerUserId)) {
//            throw new ForbiddenOperationException("You do not own this PG");
//        }
//
//
//        if (roomRepository.existsByRoomNumberAndPgId(dto.roomNumber(), pgId)) {
//            throw new ConflictException("Room number already exists in this PG");
//        }
//
//        Room room = roomMapper.toEntity(dto);
//        room.setPg(pg);
//
//        roomRepository.save(room);
//
//        log.info("Room {} created for PG {}", dto.roomNumber(), pgId);
//
//        return roomMapper.toResponseDTO(room);
//    }
//
//    @Override
//    public RoomResponseDTO updateRoom(Long roomId, RoomUpdateDTO dto) {
//
//        Room room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        Long pgId = room.getPg().getId();
//
//        if (!room.getRoomNumber().equals(dto.roomNumber())
//                && roomRepository.existsByRoomNumberAndPgId(dto.roomNumber(), pgId)) {
//            throw new ConflictException("Room number already exists in this PG");
//        }
//
//        int occupiedBeds = room.getTotalBeds() - room.getAvailableBeds();
//
//        if (dto.totalBeds() < occupiedBeds) {
//            throw new ConflictException(
//                    "Total beds cannot be less than occupied beds"
//            );
//        }
//
//        room.setRoomNumber(dto.roomNumber());
//        room.setTotalBeds(dto.totalBeds());
//        room.setAvailableBeds(dto.totalBeds() - occupiedBeds);
//
//        log.info("Room {} updated", roomId);
//
//        return roomMapper.toResponseDTO(room);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<RoomAvailabilityDTO> getRoomAvailability(Long pgId) {
//
//        if (!pgRepository.existsById(pgId)) {
//            throw new ResourceNotFoundException("PG not found");
//        }
//
//        return roomRepository.findByPgId(pgId)
//                .stream()
//                .map(room -> {
//
//                    int occupied = room.getTotalBeds() - room.getAvailableBeds();
//
//                    double percentage = room.getTotalBeds() == 0
//                            ? 0
//                            : (occupied * 100.0) / room.getTotalBeds();
//
//                    return new RoomAvailabilityDTO(
//                            pgId,
//                            room.getPg().getName(),
//                            room.getId(),
//                            room.getRoomNumber(),
//                            room.getTotalBeds(),
//                            room.getAvailableBeds(),
//                            occupied,
//                            Math.round(percentage * 100.0) / 100.0
//                    );
//                })
//                .toList();
//    }
//
//    @Override
//    public void adjustAvailableBeds(Long roomId, int delta) {
//
//        Room room = roomRepository.findByIdForUpdate(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
//
//        int newAvailable = room.getAvailableBeds() + delta;
//
//        if (newAvailable < 0 || newAvailable > room.getTotalBeds()) {
//            throw new ConflictException("Invalid bed adjustment");
//        }
//
//        room.setAvailableBeds(newAvailable);
//
//        log.debug("Room {} beds adjusted by {}", roomId, delta);
//    }
//}
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PGRepository pgRepository;

    // ======================================================
    // CREATE ROOM
    // ======================================================
    @Override
    public RoomResponseDTO createRoom(RoomCreateDTO dto, Long pgId) {

        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));

        Long ownerUserId = SecurityUtils.getCurrentUserId();

        if (!pg.getOwner().getUser().getId().equals(ownerUserId)) {
            throw new ForbiddenOperationException("You do not own this PG");
        }

        if (roomRepository.existsByRoomNumberAndPgId(dto.roomNumber(), pgId)) {
            throw new ConflictException("Room number already exists in this PG");
        }

        Room room = Room.builder()
                .roomNumber(dto.roomNumber())
                .totalBeds(dto.totalBeds())
                .availableBeds(dto.totalBeds())   // initially all beds free
                .pg(pg)
                .build();

        roomRepository.save(room);

        log.info("Room {} created for PG {}", dto.roomNumber(), pgId);

        return toResponseDTO(room);
    }

    // ======================================================
    // UPDATE ROOM
    // ======================================================
    @Override
    public RoomResponseDTO updateRoom(Long roomId, RoomUpdateDTO dto) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        Long pgId = room.getPg().getId();

        if (!room.getRoomNumber().equals(dto.roomNumber())
                && roomRepository.existsByRoomNumberAndPgId(dto.roomNumber(), pgId)) {
            throw new ConflictException("Room number already exists in this PG");
        }

        int occupiedBeds = room.getTotalBeds() - room.getAvailableBeds();

        if (dto.totalBeds() < occupiedBeds) {
            throw new ConflictException(
                    "Total beds cannot be less than occupied beds"
            );
        }

        room.setRoomNumber(dto.roomNumber());
        room.setTotalBeds(dto.totalBeds());
        room.setAvailableBeds(dto.totalBeds() - occupiedBeds);

        log.info("Room {} updated", roomId);

        return toResponseDTO(room);
    }

    // ======================================================
    // ROOM AVAILABILITY
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<RoomAvailabilityDTO> getRoomAvailability(Long pgId) {

        if (!pgRepository.existsById(pgId)) {
            throw new ResourceNotFoundException("PG not found");
        }

        return roomRepository.findByPgId(pgId)
                .stream()
                .map(room -> {

                    int occupied = room.getTotalBeds() - room.getAvailableBeds();

                    double percentage = room.getTotalBeds() == 0
                            ? 0
                            : (occupied * 100.0) / room.getTotalBeds();

                    return new RoomAvailabilityDTO(
                            pgId,
                            room.getPg().getName(),
                            room.getId(),
                            room.getRoomNumber(),
                            room.getTotalBeds(),
                            room.getAvailableBeds(),
                            occupied,
                            Math.round(percentage * 100.0) / 100.0
                    );
                })
                .toList();
    }

    // ======================================================
    // ADJUST AVAILABLE BEDS (LOCKED ROW)
    // ======================================================
    @Override
    public void adjustAvailableBeds(Long roomId, int delta) {

        Room room = roomRepository.findByIdForUpdate(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        int newAvailable = room.getAvailableBeds() + delta;

        if (newAvailable < 0 || newAvailable > room.getTotalBeds()) {
            throw new ConflictException("Invalid bed adjustment");
        }

        room.setAvailableBeds(newAvailable);

        log.debug("Room {} beds adjusted by {}", roomId, delta);
    }

    // ======================================================
    // 🔹 MANUAL MAPPER
    // ======================================================
    private RoomResponseDTO toResponseDTO(Room room) {

        int occupiedBeds = room.getTotalBeds() - room.getAvailableBeds();

        return new RoomResponseDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getTotalBeds(),
                room.getAvailableBeds(),
                occupiedBeds
        );
    }
}
