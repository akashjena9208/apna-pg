package com.apnapg.repository;

import com.apnapg.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNumberAndPgId(String roomNumber, Long pgId);

    List<Room> findByPgId(Long pgId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
    Optional<Room> findByIdForUpdate(@Param("roomId") Long roomId);

    @Query("""
        SELECT COUNT(r) > 0
        FROM Room r
        WHERE r.pg.id = :pgId
        AND r.availableBeds < r.totalBeds
    """)
    boolean hasOccupiedRooms(Long pgId);
}
