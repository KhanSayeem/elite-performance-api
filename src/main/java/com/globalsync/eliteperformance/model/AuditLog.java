package com.globalsync.eliteperformance.model;

import java.time.LocalDateTime;

public record AuditLog(Long logId, Long employeeId, String action, String performedBy, LocalDateTime timestamp) {
}
