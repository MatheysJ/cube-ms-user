package com.cube.user.services;

import com.cube.user.exceptions.ConflitException;
import com.cube.user.factory.TokenFactory;
import com.cube.user.factory.UserFactory;
import com.cube.user.mappers.UserMapper;
import com.cube.user.models.InternalUser;
import com.cube.user.dtos.request.RequestLogin;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.internal.RequestValidate;
import com.cube.user.dtos.response.ResponseUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    TokenService tokenService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private final RequestUser mockRequestUser = UserFactory.getMockOfRequestUser();
    private final ResponseUser mockResponseUser = UserFactory.getMockOfResponseUser();
    private final RequestLogin mockRequestLogin = TokenFactory.getMockOfRequestLogin();
    private final InternalUser mockInternalUser = UserFactory.getMockOfInternalUser();

    @Test
    void shouldThrowConflictExceptionWhenRegisteringWithNonUniqueMail() {
        Mockito.when(userService.getUserByMail(Mockito.anyString())).thenReturn(Optional.of(mockResponseUser));

        Assertions.assertThrows(ConflitException.class, () -> authService.register(mockRequestUser));

    }

    @Test
    void shouldReturnCreatedUserWhenUserIsRegisteredSuccessfully() {
        Mockito.when(userService.getUserByMail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userService.createUser(Mockito.any(RequestUser.class), Mockito.anyString())).thenReturn(mockResponseUser);

        ResponseUser response = authService.register(mockRequestUser);

        Assertions.assertEquals(response.getClass(), ResponseUser.class);
    }

    @Test
    void shouldCallAuthenticationManagerOnLogin() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(mockInternalUser);
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(authentication);

        authService.login(mockRequestLogin);

        Mockito.verify(tokenService, Mockito.times(1)).generateToken(Mockito.any(UserDetails.class));
    }

    @Test
    void shouldReturnSubjectAfterValidatingAValidToken() {
        String subject = "Test User";
        Mockito.when(tokenService.validateToken(Mockito.anyString())).thenReturn(subject);

        String response = authService.validate(new RequestValidate("123"));

        Assertions.assertEquals(response, subject);
    }
}
