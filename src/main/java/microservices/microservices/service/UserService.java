package microservices.microservices.service;

import microservices.microservices.model.User;
import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(String id);

    List<User> getAllUsers();

    User updateUser(String id, User user);

    void deleteUser(String id);

    // ✅ Update status (Integer)
    User updateUserStatus(String id, Integer status);

    // If you want to support String status (optional)
    default User updateUserStatus(String id, String status) {
        // convert String to Integer (example: "1" -> 1)
        Integer intStatus;
        try {
            intStatus = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid status value");
        }
        return updateUserStatus(id, intStatus);
    }

    // ✅ Total Users Count
    long getUsersCount();
}
