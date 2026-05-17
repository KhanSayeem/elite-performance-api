package com.globalsync.eliteperformance.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PerformanceCalculationRequest(
        @NotNull Long employeeId,
        @Min(2000) int reviewYear,
        @Min(0) @Max(25) int taskCompletion,
        @Min(0) @Max(15) int attendance,
        @Min(0) @Max(15) int teamCollaboration,
        @Min(0) @Max(15) int problemSolving,
        @Min(0) @Max(10) int communication,
        @Min(0) @Max(10) int leadership,
        @Min(0) @Max(10) int clientSatisfaction
) {
    public KpiScores toScores() {
        return new KpiScores(taskCompletion, attendance, teamCollaboration, problemSolving,
                communication, leadership, clientSatisfaction);
    }
}
