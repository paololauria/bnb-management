package com.paololauria.bnb.services.implementations;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.repository.abstractions.UserRepository;
import com.paololauria.bnb.services.abstraction.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPAUserService implements UserService {
    private final UserRepository userRepository;
    public JPAUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User newUser, User user) {
        userRepository.save(newUser);
    }

    @Override
    public String updateUserProfileImage(Long userId, String imageUrl) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setImage(imageUrl);
            User savedUser = userRepository.save(user);
            return savedUser.getImage();
        } else {
            throw new RuntimeException("Utente con ID: " + userId + " non trovato");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

}