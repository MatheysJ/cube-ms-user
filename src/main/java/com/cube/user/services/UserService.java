package com.cube.user.services;

import com.cube.user.mappers.UserMapper;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.internal.Role;
import com.cube.user.models.internal.InternalUser;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseUser createUser(RequestUser body) {
        InternalUser internalUser = userRepository.save( InternalUser.builder()
                .name(body.getName())
                .mail(body.getMail())
                .password(body.getPassword())
                .profilePicture(body.getProfilePicture())
                .role(Role.USER)
                .build());

        return userMapper.internalToResponse(internalUser);
    }

    public List<ResponseUser> getAllUsers() {
        Stream<InternalUser> users = userRepository.findAll().stream();

        return users
                .map(userMapper::internalToResponse)
                .collect(Collectors.toList());

    }

    public Optional<ResponseUser> getUserById(Long id) {

        Optional<InternalUser> internalUser = userRepository.findById(id);

        return internalUser.map(userMapper::internalToResponse);
    }

    public Optional<ResponseUser> getUserByMail(String mail) {

        Optional<InternalUser> internalUser = userRepository.findByMail(mail);

        return internalUser.map(userMapper::internalToResponse);
    }

    public Optional<ResponseUser> editUserById(Long id, RequestUser body) {
        Optional<InternalUser> internalUser = userRepository.findById(id).map(user -> {
            InternalUser.builder()
                    .name(body.getName())
                    .mail(body.getMail())
                    .password(body.getPassword())
                    .profilePicture(body.getProfilePicture())
                    .role(Role.USER)
                    .build();
            return userRepository.save(user);
        });

        return internalUser.map(userMapper::internalToResponse);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
