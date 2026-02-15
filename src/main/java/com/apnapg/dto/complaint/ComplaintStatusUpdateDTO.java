package com.apnapg.dto.complaint;

import com.apnapg.enums.ComplaintStatus;
import jakarta.validation.constraints.NotNull;

public record ComplaintStatusUpdateDTO(

        @NotNull
        ComplaintStatus status

) {}
