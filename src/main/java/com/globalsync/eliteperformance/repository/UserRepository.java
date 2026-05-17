package com.globalsync.eliteperformance.repository;

import com.globalsync.eliteperformance.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User save(User user);
}
