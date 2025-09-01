package microservices.microservices.service;

import microservices.microservices.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(String id);
    List<User> getAllUsers();
    User updateUser(String id, User user);
    void deleteUser(String id);

    // ✅ New method for status update
    User updateUserStatus(String id, String status);

    // ✅ Only status update
    User updateUserStatus(String id, Integer status);
}
