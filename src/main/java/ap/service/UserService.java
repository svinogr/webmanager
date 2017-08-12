package ap.service;

import ap.entity.User;

import java.util.List;

public interface UserService {
    boolean createNewUser(User user);
    User getUserById(int id);
    boolean deleteUser(User user);
    boolean updateUser(User user);
    List<User> getAll();
}
