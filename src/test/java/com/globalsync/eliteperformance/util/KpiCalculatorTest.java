package com.globalsync.eliteperformance.util;

import com.globalsync.eliteperformance.dto.KpiScores;
import com.globalsync.eliteperformance.enums.EmployeeCategory;
import com.globalsync.eliteperformance.exception.InvalidKpiScoreException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KpiCalculatorTest {

    private final KpiCalculator calculator = new KpiCalculator();

    @Test
    void sumsAllSevenKpiScoresWithStreams() {
        KpiScores scores = new KpiScores(20, 14, 13, 12, 9, 8, 7);

        int total = calculator.calculateTotal(scores);

        assertEquals(83, total);
    }

    @Test
    void rejectsScoresAboveConfiguredMaximum() {
        KpiScores scores = new KpiScores(26, 14, 13, 12, 9, 8, 7);

        assertThrows(InvalidKpiScoreException.class, () -> calculator.calculateTotal(scores));
    }

    @Test
    void mapsTotalScoreToExpectedCategory() {
        assertEquals(EmployeeCategory.GOLD, calculator.categoryFor(90));
        assertEquals(EmployeeCategory.SILVER, calculator.categoryFor(75));
        assertEquals(EmployeeCategory.BRONZE, calculator.categoryFor(60));
        assertEquals(EmployeeCategory.NO_TIER, calculator.categoryFor(59));
    }
}
