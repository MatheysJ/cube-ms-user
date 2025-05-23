package com.cube.user.services;

import com.cube.user.dtos.response.InternalResponseUser;
import com.cube.user.exceptions.NotFoundException;
import com.cube.user.mappers.UserMapper;
import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.internal.Role;
import com.cube.user.models.InternalUser;
import com.cube.user.dtos.response.ResponseUser;
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

    public ResponseUser createUser(RequestUser body, String asaasId) {
        InternalUser internalUser = userMapper.requestToInternal(body);
        internalUser.setRole(Role.USER);
        internalUser.setAsaasId(asaasId);

        InternalUser newUser = userRepository.save(internalUser);

        return userMapper.internalToResponse(newUser);
    }

    public List<ResponseUser> getAllUsers() {
        Stream<InternalUser> users = userRepository.findAll().stream();

        return users.map(userMapper::internalToResponse).collect(Collectors.toList());
    }

    public InternalResponseUser getUserById(Long id) {
        InternalUser internalUser = getInternalUserByIdOrThrow(id);

        return userMapper.internalToInternalResponse(internalUser);
    }

    public Optional<ResponseUser> getUserByMail(String mail) {

        Optional<InternalUser> internalUser = userRepository.findByMail(mail);

        return internalUser.map(userMapper::internalToResponse);
    }
    public Optional<ResponseUser> getUserByCpfCnpj(String cpfCnpj) {

        Optional<InternalUser> internalUser = userRepository.findByCpfCnpj(cpfCnpj);

        return internalUser.map(userMapper::internalToResponse);
    }

    public ResponseUser editUserById(Long id, RequestUser body) {
        InternalUser internalUser = getInternalUserByIdOrThrow(id);

        InternalUser editedUser = userRepository.save(InternalUser.builder()
                .name(body.getName())
                .mail(body.getMail())
                .password(body.getPassword())
                .role(Role.USER)
                .id(internalUser.getId())
                .build());

        return userMapper.internalToResponse(editedUser);
    }

    public void deleteUserById(Long id) {
        getInternalUserByIdOrThrow(id);

        userRepository.deleteById(id);
    }

    private InternalUser getInternalUserByIdOrThrow(Long id) throws NotFoundException {
        Optional<InternalUser> internalUser = userRepository.findById(id);

        if (internalUser.isEmpty()) {
            throw new NotFoundException(ExceptionCode.USER_WITH_ID_DOESNT_EXIST);
        }

        return internalUser.get();
    }
}
