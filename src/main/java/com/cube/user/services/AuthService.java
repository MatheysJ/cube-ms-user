package com.cube.user.services;

import com.cube.user.exceptions.BusinessException;
import com.cube.user.models.internal.ExceptionCode;
import com.cube.user.models.request.RequestLogin;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.response.ResponseUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public ResponseUser register(RequestUser requestUser) {
        if (this.userService.getUserByMail(requestUser.getMail()).isPresent()) {
            throw new BusinessException(ExceptionCode.ALREADY_EXISTS);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(requestUser.getPassword());
        requestUser.setPassword(encryptedPassword);

        return userService.createUser(requestUser);
    }

    public void login(RequestLogin requestLogin) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                requestLogin.getMail(),
                requestLogin.getPassword()
        );

        this.authenticationManager.authenticate(token);
    }

}
