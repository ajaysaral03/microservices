package microservices.microservices.controller;

import microservices.microservices.dto.ApiResponse;
import microservices.microservices.dto.LoginRequest;
import microservices.microservices.model.User;
import microservices.microservices.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Email already exists"));
            }

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Password cannot be empty"));
            }

            if (user.getStatus() == null) user.setStatus(1); // active by default

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>("User registered successfully", savedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        }
    }

    // ✅ LOGIN (role only from DB)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Invalid email or password"));
        }

        if (user.getStatus() == null || user.getStatus() != 1) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("User is not active"));
        }

        // ✅ Role always taken from DB
        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("User does not have a role assigned"));
        }

        // ✅ Generate token (UUID for now, replace with JWT later)
        String token = java.util.UUID.randomUUID().toString();

        Map<String, Object> loginData = new HashMap<>();
        loginData.put("id", user.getId());
        loginData.put("name", user.getName());
        loginData.put("email", user.getEmail());
        loginData.put("role", role);
        loginData.put("status", user.getStatus() == 1 ? "active" : "inactive");
        loginData.put("token", token);

        return ResponseEntity.ok(new ApiResponse<>("Login successful", loginData));
    }

    // ✅ LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(new ApiResponse<>("Logged out successfully"));
    }
}
