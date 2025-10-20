package com.apnapg.dto;

import com.apnapg.enums.ComplaintStatus;

public record ComplaintStatusUpdateDTO(
        ComplaintStatus status
) {}
