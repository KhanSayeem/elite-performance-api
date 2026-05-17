package com.globalsync.eliteperformance.dto;

import com.globalsync.eliteperformance.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull Long employeeId,
        @NotBlank String username,
        @NotBlank String password,
        @NotNull Role role
) {
}
