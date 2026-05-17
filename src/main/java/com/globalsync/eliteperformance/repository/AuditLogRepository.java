package com.globalsync.eliteperformance.repository;

public interface AuditLogRepository {
    void save(Long employeeId, String action, String performedBy);
}
