package com.apnapg.mapper;

import com.apnapg.dto.RoomCreateDTO;
import com.apnapg.entity.PG;
import com.apnapg.entity.Room;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomMapper {

    public Room toEntity(RoomCreateDTO dto, PG pg) {
        return Room.builder()
                .roomNumber(dto.roomNumber())
                .totalBeds(dto.totalBeds())
                .availableBeds(dto.totalBeds())
                .pg(pg)
                .build();
    }
}
