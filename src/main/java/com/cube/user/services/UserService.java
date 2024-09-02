package com.cube.user.services;

import com.cube.user.models.User;
import com.cube.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> editUserById(Long id, User body) {
        return userRepository.findById(id).map(user -> {
            user.setName(body.getName());
            user.setMail(body.getMail());
            user.setPassword(body.getPassword());
            user.setProfilePicture(body.getProfilePicture());
            return userRepository.save(user);
        });
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
