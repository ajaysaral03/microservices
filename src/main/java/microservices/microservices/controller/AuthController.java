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

            // Hash password before saving
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Password cannot be empty"));
            }

            // Set default status (1 = active)
            if (user.getStatus() == null) {
                user.setStatus(1);
            }

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>("User registered successfully", savedUser));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        }
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Invalid email or password"));
        }

        // Check if user is active
        if (user.getStatus() == null || user.getStatus() != 1) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("User is not active"));
        }

        // Prepare login response
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("id", user.getId());
        loginData.put("name", user.getName());
        loginData.put("email", user.getEmail());
        loginData.put("role", user.getRole());
        loginData.put("status", user.getStatus() == 1 ? "active" : "inactive");

        return ResponseEntity.ok(new ApiResponse<>("Login successful", loginData));
    }
}
