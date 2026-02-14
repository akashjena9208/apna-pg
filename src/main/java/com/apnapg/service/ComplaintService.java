package com.apnapg.service;

import com.apnapg.dto.complaint.*;

import java.util.List;

public interface ComplaintService {

    ComplaintResponseDTO createComplaint(ComplaintCreateDTO dto, Long tenantId);

    void updateStatus(Long complaintId, ComplaintStatusUpdateDTO dto);

    List<ComplaintResponseDTO> getTenantComplaints(Long tenantId);

    List<ComplaintResponseDTO> getPGComplaints(Long pgId);
}
