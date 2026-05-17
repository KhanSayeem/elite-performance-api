package com.globalsync.eliteperformance.util;

import com.globalsync.eliteperformance.dto.KpiScores;
import com.globalsync.eliteperformance.enums.EmployeeCategory;
import com.globalsync.eliteperformance.exception.InvalidKpiScoreException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class KpiCalculator {

    private record ScoreLimit(String name, int value, int maximum) {
    }

    public int calculateTotal(KpiScores scores) {
        List<ScoreLimit> limits = List.of(
                new ScoreLimit("taskCompletion", scores.taskCompletion(), 25),
                new ScoreLimit("attendance", scores.attendance(), 15),
                new ScoreLimit("teamCollaboration", scores.teamCollaboration(), 15),
                new ScoreLimit("problemSolving", scores.problemSolving(), 15),
                new ScoreLimit("communication", scores.communication(), 10),
                new ScoreLimit("leadership", scores.leadership(), 10),
                new ScoreLimit("clientSatisfaction", scores.clientSatisfaction(), 10)
        );

        IntStream.range(0, limits.size()).forEach(index -> validate(limits.get(index)));
        return limits.stream().mapToInt(ScoreLimit::value).sum();
    }

    public EmployeeCategory categoryFor(int totalScore) {
        if (totalScore < 0 || totalScore > 100) {
            throw new InvalidKpiScoreException("Total KPI score must be between 0 and 100");
        }
        if (totalScore >= 90) {
            return EmployeeCategory.GOLD;
        }
        if (totalScore >= 75) {
            return EmployeeCategory.SILVER;
        }
        if (totalScore >= 60) {
            return EmployeeCategory.BRONZE;
        }
        return EmployeeCategory.NO_TIER;
    }

    private void validate(ScoreLimit limit) {
        if (limit.value() < 0 || limit.value() > limit.maximum()) {
            throw new InvalidKpiScoreException(limit.name() + " must be between 0 and " + limit.maximum());
        }
    }
}
