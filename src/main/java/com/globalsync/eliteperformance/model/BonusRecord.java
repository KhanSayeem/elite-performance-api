package com.globalsync.eliteperformance.model;

import com.globalsync.eliteperformance.enums.EmployeeCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BonusRecord(
        Long bonusId,
        Long employeeId,
        int reviewYear,
        int totalKpiScore,
        EmployeeCategory category,
        int bonusPercentage,
        BigDecimal bonusAmount,
        BigDecimal totalCompensation,
        LocalDateTime createdAt
) {
}
