package com.apnapg.repositories;

import com.apnapg.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
  List<Complaint> findByPgId(Long pgId);
  List<Complaint> findByTenantId(Long tenantId);
}
