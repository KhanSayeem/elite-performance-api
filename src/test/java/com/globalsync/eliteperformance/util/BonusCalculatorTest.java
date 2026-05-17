package com.globalsync.eliteperformance.util;

import com.globalsync.eliteperformance.enums.EmployeeCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusCalculatorTest {

    private final BonusCalculator calculator = new BonusCalculator();

    @Test
    void calculatesBonusAmountFromSalaryAndCategoryPercentage() {
        BigDecimal bonus = calculator.bonusAmount(new BigDecimal("100000.00"), EmployeeCategory.GOLD);

        assertEquals(new BigDecimal("20000.00"), bonus);
    }

    @Test
    void calculatesTotalCompensationIncludingBonus() {
        BigDecimal total = calculator.totalCompensation(new BigDecimal("100000.00"), EmployeeCategory.SILVER);

        assertEquals(new BigDecimal("112000.00"), total);
    }
}
