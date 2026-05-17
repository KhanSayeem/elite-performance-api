package com.globalsync.eliteperformance.dto;

import com.globalsync.eliteperformance.enums.Role;

public record AuthResponse(String token, String username, Role role, Long employeeId) {
}
