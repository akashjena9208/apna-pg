//package com.apnapg.repository;
//import com.apnapg.entity.PG;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.math.BigDecimal;
//public interface PGRepository extends JpaRepository<PG, Long> {
//
//    Page<PG> findByCityIgnoreCase(String city, Pageable pageable);
//    Page<PG> findByCityIgnoreCaseAndRentPerMonthBetween(
//            String city,
//            BigDecimal minRent,
//            BigDecimal maxRent,
//            Pageable pageable
//    );
//    boolean existsByNameIgnoreCaseAndCityIgnoreCase(String name, String city);
//    Page<PG> findByCityIgnoreCaseAndRentPerMonthBetween(
//            String city,
//            Double minRent,
//            Double maxRent,
//            Pageable pageable
//    );
//
//}

package com.apnapg.repository;

import com.apnapg.entity.PG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PGRepository extends JpaRepository<PG, Long> {

    // ===============================
    // Search by City
    // ===============================
    Page<PG> findByCityIgnoreCase(
            String city,
            Pageable pageable
    );

    // ===============================
    // Search by City + Rent Range
    // ===============================
    Page<PG> findByCityIgnoreCaseAndRentPerMonthBetween(
            String city,
            BigDecimal minRent,
            BigDecimal maxRent,
            Pageable pageable
    );

    // ===============================
    // Prevent duplicate PG in same city
    // ===============================
    boolean existsByNameIgnoreCaseAndCityIgnoreCase(
            String name,
            String city
    );
}
