package com.globalsync.eliteperformance.repository;

import com.globalsync.eliteperformance.model.PerformanceReview;

public interface PerformanceReviewRepository {
    boolean existsByEmployeeIdAndReviewYear(Long employeeId, int reviewYear);

    PerformanceReview save(PerformanceReview review);
}
