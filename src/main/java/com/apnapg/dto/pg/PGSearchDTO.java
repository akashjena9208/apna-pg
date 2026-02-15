package com.apnapg.dto.pg;
import java.math.BigDecimal;
public record PGSearchDTO(
        String city,
        BigDecimal minRent,
        BigDecimal maxRent

) {}
