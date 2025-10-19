package com.apnapg.repositories;

import com.apnapg.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByEmail(String email);
    // Fetch all tenants who belong to rooms in PGs of a specific owner
    List<Tenant> findAllByRoom_Pg_Owner_Id(Long ownerId);



    // Custom query to fetch tenants by Owner ID through Room -> PG -> Owner
//    @Query("""
//           SELECT t FROM Tenant t
//           JOIN t.room r
//           JOIN r.pg p
//           JOIN p.owner o
//           WHERE o.id = :ownerId
//           """)
//    List<Tenant> findAllByOwnerId(@Param("ownerId") Long ownerId);
}


