package com.globalsync.eliteperformance.dto;

public record KpiScores(
        int taskCompletion,
        int attendance,
        int teamCollaboration,
        int problemSolving,
        int communication,
        int leadership,
        int clientSatisfaction
) {
}
