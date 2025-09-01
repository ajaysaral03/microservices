package microservices.microservices.service;

import microservices.microservices.model.User;
import microservices.microservices.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        User existingUser = getUserById(id);
        userRepository.delete(existingUser);
    }

    @Override
    public User updateUserStatus(String id, String status) {
        return null;
    }

    // ✅ Only status update
    @Override
    public User updateUserStatus(String id, Integer status) {
        User existing = getUserById(id);
        existing.setStatus(status);
        return userRepository.save(existing);
    }
}
