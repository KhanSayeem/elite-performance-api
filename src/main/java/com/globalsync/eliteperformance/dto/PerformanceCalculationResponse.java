package com.globalsync.eliteperformance.dto;

import com.globalsync.eliteperformance.enums.EmployeeCategory;

import java.math.BigDecimal;

public record PerformanceCalculationResponse(
        Long employeeId,
        int reviewYear,
        int totalKpiScore,
        EmployeeCategory category,
        int bonusPercentage,
        BigDecimal bonusAmount,
        BigDecimal totalCompensation
) {
}
