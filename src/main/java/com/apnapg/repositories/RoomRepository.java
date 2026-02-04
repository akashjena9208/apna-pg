package com.apnapg.repositories;

import com.apnapg.entity.Room;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByPg_Owner_Id(Long ownerId);
}