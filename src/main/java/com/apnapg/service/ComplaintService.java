package com.apnapg.service;

import com.apnapg.dto.ComplaintDTO;
import com.apnapg.dto.ComplaintStatusUpdateDTO;
import com.apnapg.entity.Complaint;
import com.apnapg.entity.PG;
import com.apnapg.entity.Tenant;
import com.apnapg.mapper.ComplaintMapper;
import com.apnapg.repositories.ComplaintRepository;
import com.apnapg.repositories.PGRepository;
import com.apnapg.repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final TenantRepository tenantRepository;
    private final PGRepository pgRepository;

    public Complaint createComplaint(ComplaintDTO dto) {
        Tenant tenant = tenantRepository.findById(dto.tenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        PG pg = pgRepository.findById(dto.pgId())
                .orElseThrow(() -> new RuntimeException("PG not found"));
        Complaint complaint = ComplaintMapper.toEntity(dto, tenant, pg);
        return complaintRepository.save(complaint);
    }

    public Complaint updateComplaintStatus(Long complaintId, ComplaintStatusUpdateDTO dto) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(dto.status());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getPGComplaints(Long pgId) {
        return complaintRepository.findByPgId(pgId);
    }

    public List<Complaint> getTenantComplaints(Long tenantId) {
        return complaintRepository.findByTenantId(tenantId);
    }
}
