package com.apnapg.service;

import com.apnapg.dto.RoomCreateDTO;
import com.apnapg.entity.PG;
import com.apnapg.entity.Room;
import com.apnapg.mapper.RoomMapper;
import com.apnapg.repositories.PGRepository;
import com.apnapg.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final PGRepository pgRepository;

    @Transactional
    public Room addRoom(Long pgId, RoomCreateDTO dto) {
        log.info("Adding room '{}' to PG {}", dto.roomNumber(), pgId);

        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new IllegalArgumentException("PG not found with id " + pgId));

        Room room = RoomMapper.toEntity(dto, pg);
        Room savedRoom = roomRepository.save(room);

        log.info("Room '{}' added successfully to PG '{}'", savedRoom.getRoomNumber(), pg.getName());
        return savedRoom;
    }

    @Transactional
    public void allocateRoomBed(Long roomId, int beds) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id " + roomId));

        if (room.getAvailableBeds() < beds) {
            throw new IllegalArgumentException("Not enough available beds in room " + room.getRoomNumber());
        }

        room.setAvailableBeds(room.getAvailableBeds() - beds);
        roomRepository.save(room);

        log.info("{} bed(s) allocated in room {}", beds, room.getRoomNumber());
    }
}
