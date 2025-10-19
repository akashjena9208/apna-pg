package com.apnapg.repositories;

import com.apnapg.entity.PG;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PGRepository extends JpaRepository<PG, Long> {
    List<PG> findByOwnerId(Long ownerId);

    List<PG> findByCityContainingIgnoreCaseAndRentPerMonthLessThanEqual(String city, int rentMax);

}