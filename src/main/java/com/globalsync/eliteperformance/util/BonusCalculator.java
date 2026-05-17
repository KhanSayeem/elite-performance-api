package com.globalsync.eliteperformance.util;

import com.globalsync.eliteperformance.enums.EmployeeCategory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BonusCalculator {

    public BigDecimal bonusAmount(BigDecimal baseSalary, EmployeeCategory category) {
        return baseSalary
                .multiply(BigDecimal.valueOf(category.getBonusPercentage()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal totalCompensation(BigDecimal baseSalary, EmployeeCategory category) {
        return baseSalary.add(bonusAmount(baseSalary, category)).setScale(2, RoundingMode.HALF_UP);
    }
}
