
package com.apnapg.service.impl;

import com.apnapg.dto.complaint.*;
import com.apnapg.entity.Complaint;
import com.apnapg.entity.PG;
import com.apnapg.entity.Tenant;
import com.apnapg.enums.ComplaintStatus;
import com.apnapg.exceptions.BadRequestException;
import com.apnapg.exceptions.ConflictException;
import com.apnapg.exceptions.ForbiddenOperationException;
import com.apnapg.exceptions.ResourceNotFoundException;
import com.apnapg.repository.ComplaintRepository;
import com.apnapg.repository.PGRepository;
import com.apnapg.repository.TenantRepository;
import com.apnapg.security.SecurityUtils;
import com.apnapg.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final TenantRepository tenantRepository;
    private final PGRepository pgRepository;

    // ======================================================
    // CREATE COMPLAINT (TENANT ONLY)
    // ======================================================
    @Override
    public ComplaintResponseDTO createComplaint(
            ComplaintCreateDTO dto,
            Long ignoredTenantId
    ) {

        Long userId = SecurityUtils.getCurrentUserId();

        Tenant tenant = tenantRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        // 🔥 1️⃣ Tenant must have room
        if (tenant.getRoom() == null) {
            throw new BadRequestException("You are not allocated to any room");
        }

        PG pgFromRoom = tenant.getRoom().getPg();

        // 🔥 2️⃣ Validate PG belongs to tenant room
        if (!pgFromRoom.getId().equals(dto.pgId())) {
            throw new ForbiddenOperationException("You are not assigned to this PG");
        }

        // 🔥 3️⃣ Prevent duplicate active complaint
        boolean alreadyExists = complaintRepository
                .existsByTenantAndPgAndStatus(
                        tenant,
                        pgFromRoom,
                        ComplaintStatus.NEW
                );

        if (alreadyExists) {
            throw new ConflictException("You already have an active complaint for this PG");
        }

        Complaint complaint = Complaint.builder()
                .message(dto.message())
                .status(ComplaintStatus.NEW)
                .tenant(tenant)
                .pg(pgFromRoom)
                .build();

        complaintRepository.save(complaint);

        log.info("Complaint created by tenant {} for PG {}",
                tenant.getId(),
                pgFromRoom.getId());

        return toResponseDTO(complaint);
    }


    //This code is Coorect but some exception not cheked its woring
//    @Override
//    public ComplaintResponseDTO createComplaint(
//            ComplaintCreateDTO dto,
//            Long ignoredTenantId
//    ) {
//
//
//        Long userId = SecurityUtils.getCurrentUserId();
//
//        Tenant tenant = tenantRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
//
//        if (!tenant.getRoom().getPg().getId().equals(dto.pgId())) {
//            throw new ForbiddenOperationException("You are not assigned to this PG");
//        }
//
//        PG pg = pgRepository.findById(dto.pgId())
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));
//
//        Complaint complaint = Complaint.builder()
//                .message(dto.message())
//                .status(ComplaintStatus.NEW)
//                .tenant(tenant)
//                .pg(pg)
//                .build();
//
//        complaintRepository.save(complaint);
//
//        log.info("Complaint created by tenant {} for PG {}", tenant.getId(), dto.pgId());
//
//        return toResponseDTO(complaint);
//    }

    // ======================================================
    // UPDATE STATUS (OWNER ONLY)
    // ======================================================
    @Override
    public void updateStatus(Long complaintId, ComplaintStatusUpdateDTO dto) {


        Long userId = SecurityUtils.getCurrentUserId();
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        Long currentOwnerId = SecurityUtils.getCurrentUserId();
        if (!complaint.getPg().getOwner().getUser().getId().equals(currentOwnerId)) {
            throw new ForbiddenOperationException("You do not own this PG");
        }


        if (!complaint.getPg().getOwner().getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("You do not own this PG");
        }

        complaint.setStatus(dto.status());

        log.info("Complaint {} updated to {}", complaintId, dto.status());
    }

    // ======================================================
    // GET TENANT COMPLAINTS (SELF ONLY)
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDTO> getTenantComplaints(Long tenantId) {

        Long userId = SecurityUtils.getCurrentUserId();

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        if (!tenant.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Access denied");
        }

        return complaintRepository.findByTenant_Id(tenantId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ======================================================
    // GET PG COMPLAINTS (OWNER ONLY)
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponseDTO> getPGComplaints(Long pgId) {

        Long userId = SecurityUtils.getCurrentUserId();

        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new ResourceNotFoundException("PG not found"));

        if (!pg.getOwner().getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("You do not own this PG");
        }

        return complaintRepository.findByPg_Id(pgId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ======================================================
    // 🔹 MANUAL MAPPER
    // ======================================================
    private ComplaintResponseDTO toResponseDTO(Complaint complaint) {

        return new ComplaintResponseDTO(
                complaint.getId(),
                complaint.getPg().getId(),
                complaint.getTenant().getId(),
                complaint.getMessage(),
                complaint.getStatus(),
                complaint.getCreatedAt(),
                complaint.getUpdatedAt()
        );
    }
}
