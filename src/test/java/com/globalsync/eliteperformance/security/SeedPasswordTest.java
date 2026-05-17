package com.globalsync.eliteperformance.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SeedPasswordTest {

    @Test
    void sampleUserHashMatchesDocumentedPassword() {
        String seededHash = "$2a$10$wam5UR.VMvuhRElr5s39zeGJqCsILj4./y7zER43L1zSm3bZCsAKm";

        assertTrue(new BCryptPasswordEncoder().matches("password", seededHash));
    }
}
