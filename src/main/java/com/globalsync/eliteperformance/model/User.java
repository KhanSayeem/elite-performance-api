package com.globalsync.eliteperformance.model;

import com.globalsync.eliteperformance.enums.Role;

public record User(Long userId, Long employeeId, String username, String password, Role role) {
}
