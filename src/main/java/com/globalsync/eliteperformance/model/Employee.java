package com.globalsync.eliteperformance.model;

import com.globalsync.eliteperformance.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Employee(
        Long employeeId,
        String name,
        String designation,
        BigDecimal baseSalary,
        Role role,
        LocalDate lastPromotionDate
) {
}
