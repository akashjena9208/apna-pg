package com.apnapg.dto;

public record ComplaintDTO(
        Long tenantId,
        Long pgId,
        String message
) {}
