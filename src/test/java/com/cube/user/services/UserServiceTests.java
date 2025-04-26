package com.cube.user.services;

import com.cube.user.exceptions.NotFoundException;
import com.cube.user.factory.UserFactory;
import com.cube.user.mappers.UserMapper;
import com.cube.user.models.InternalUser;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private final InternalUser mockInternalUser = UserFactory.getMockOfInternalUser();
    private final ResponseUser mockResponseUser = UserFactory.getMockOfResponseUser();
    private final RequestUser mockRequestUser = UserFactory.getMockOfRequestUser();
    private final String mockAsaasId = UserFactory.getMockOfAsaasId();

    @Test
    void shouldGetAllUsersAndReturnCorrectData () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(mockInternalUser));

        List<ResponseUser> response = userService.getAllUsers();

        Assertions.assertEquals(response.getFirst().getName(), mockInternalUser.getName());
    }

    @Test
    void shouldGetAllUsersAndMapItToResponseUser () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(mockInternalUser));

        List<ResponseUser> response = userService.getAllUsers();

        Assertions.assertEquals(response.getFirst().getClass(), ResponseUser.class);
    }

    @Test
    void shouldCreateUserWithRequest () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.save(Mockito.any(InternalUser.class))).thenReturn(mockInternalUser);

        ResponseUser response = userService.createUser(mockRequestUser, mockAsaasId);

        Assertions.assertEquals(response.getClass(), ResponseUser.class);
    }

    @Test
    void shouldGetUserById () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockInternalUser));

        ResponseUser response = userService.getUserById(1L);

        Assertions.assertEquals(response.getClass(), ResponseUser.class);
    }

    @Test
    void shouldThrowNotFoundWhenThereIsNoUserWithSpecifiedId () {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void shouldGetUserByMail () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.of(mockInternalUser));

        Optional<ResponseUser> response = userService.getUserByMail("A@B");

        //noinspection OptionalGetWithoutIsPresent
        Assertions.assertEquals(response.get().getClass(), ResponseUser.class);
    }

    @Test
    void shouldReturnEmptyWhenUserIsNotFoundByMail () {
        Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.empty());

        Optional<ResponseUser> response = userService.getUserByMail("A@B");

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void shouldReturnNewUserDataWhenEditingUserById () {
        Mockito.when(userMapper.internalToResponse(Mockito.any())).thenReturn(mockResponseUser);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockInternalUser));

        ResponseUser response = userService.editUserById(1L, mockRequestUser);

        Assertions.assertEquals(response.getClass(), ResponseUser.class);
    }

    @Test
    void shouldThrowNotFoundErrorWhenTryingToEditUnknownUser () {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.editUserById(1L, mockRequestUser));
    }

    @Test
    void shouldCallDeleteByIdWhenTryingToDeleteKnownUser () {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockInternalUser));

        userService.deleteUserById(1L);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void shouldThrowNotFoundErrorWhenTryingToDeleteUnknownUser () {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.deleteUserById(1L));
    }
}
