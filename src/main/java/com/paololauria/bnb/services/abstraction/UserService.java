package com.paololauria.bnb.services.abstraction;
import com.paololauria.bnb.model.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUser();

    Optional<User> findById(Long id);

    void createUser(User newUser, User user);

    String updateUserProfileImage(Long userId, String imageUrl);
}
