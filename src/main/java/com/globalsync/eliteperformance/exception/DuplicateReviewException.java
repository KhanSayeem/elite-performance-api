package com.globalsync.eliteperformance.exception;

public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(Long employeeId, int reviewYear) {
        super("Review already exists for employee " + employeeId + " in " + reviewYear);
    }
}
