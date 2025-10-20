package com.apnapg.mapper;

import com.apnapg.dto.ComplaintDTO;
import com.apnapg.entity.Complaint;
import com.apnapg.entity.PG;
import com.apnapg.entity.Tenant;

public class ComplaintMapper {
    public static Complaint toEntity(ComplaintDTO dto, Tenant tenant, PG pg) {
        return Complaint.builder()
                .tenant(tenant)
                .pg(pg)
                .message(dto.message())
                .status(com.apnapg.enums.ComplaintStatus.NEW)
                .build();
    }
}
