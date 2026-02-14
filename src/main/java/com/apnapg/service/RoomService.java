//package com.apnapg.service;
//import com.apnapg.dto.room.*;
//import java.util.List;
//
//public interface RoomService {
//
//    RoomResponseDTO createRoom(RoomCreateDTO dto, Long pgId);
//
//    RoomResponseDTO updateRoom(Long roomId, RoomCreateDTO dto);
//
//    List<RoomAvailabilityDTO> getRoomAvailability(Long pgId);
//
//    void adjustAvailableBeds(Long roomId, int beds);
//}
package com.apnapg.service;

import com.apnapg.dto.room.*;

import java.util.List;

public interface RoomService {

    RoomResponseDTO createRoom(RoomCreateDTO dto, Long pgId);

    RoomResponseDTO updateRoom(Long roomId, RoomUpdateDTO dto);

    List<RoomAvailabilityDTO> getRoomAvailability(Long pgId);

    void adjustAvailableBeds(Long roomId, int delta);
}
