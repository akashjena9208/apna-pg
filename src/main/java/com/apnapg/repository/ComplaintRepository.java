package com.apnapg.repository;

import com.apnapg.entity.Complaint;
import com.apnapg.entity.PG;
import com.apnapg.entity.Tenant;
import com.apnapg.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

  List<Complaint> findByPg_Id(Long pgId);
  List<Complaint> findByTenant_Id(Long tenantId);
  boolean existsByTenantAndPgAndStatus(
          Tenant tenant,
          PG pg,
          ComplaintStatus status
  );


}
