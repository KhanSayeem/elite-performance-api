package com.globalsync.eliteperformance.model;

import com.globalsync.eliteperformance.enums.EmployeeCategory;

import java.time.LocalDateTime;

public record PerformanceReview(
        Long reviewId,
        Long employeeId,
        int reviewYear,
        int taskCompletion,
        int attendance,
        int teamCollaboration,
        int problemSolving,
        int communication,
        int leadership,
        int clientSatisfaction,
        int totalKpiScore,
        EmployeeCategory category,
        LocalDateTime createdAt
) {
}
