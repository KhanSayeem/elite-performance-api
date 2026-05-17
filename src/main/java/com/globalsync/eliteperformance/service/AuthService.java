package com.globalsync.eliteperformance.service;

import com.globalsync.eliteperformance.dto.AuthResponse;
import com.globalsync.eliteperformance.dto.LoginRequest;
import com.globalsync.eliteperformance.dto.RegisterRequest;
import com.globalsync.eliteperformance.exception.EmployeeNotFoundException;
import com.globalsync.eliteperformance.exception.ResourceConflictException;
import com.globalsync.eliteperformance.model.User;
import com.globalsync.eliteperformance.repository.EmployeeRepository;
import com.globalsync.eliteperformance.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       EmployeeRepository employeeRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (!employeeRepository.existsById(request.employeeId())) {
            throw new EmployeeNotFoundException(request.employeeId());
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceConflictException("Username already exists");
        }
        User saved = userRepository.save(new User(null, request.employeeId(), request.username(),
                passwordEncoder.encode(request.password()), request.role()));
        String token = jwtService.generateToken(saved.username(), saved.role(), saved.employeeId());
        return new AuthResponse(token, saved.username(), saved.role(), saved.employeeId());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        if (!passwordEncoder.matches(request.password(), user.password())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        String token = jwtService.generateToken(user.username(), user.role(), user.employeeId());
        return new AuthResponse(token, user.username(), user.role(), user.employeeId());
    }
}
