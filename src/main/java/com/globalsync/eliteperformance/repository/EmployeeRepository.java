package com.globalsync.eliteperformance.repository;

import com.globalsync.eliteperformance.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();

    Optional<Employee> findById(Long employeeId);

    boolean existsById(Long employeeId);
}
