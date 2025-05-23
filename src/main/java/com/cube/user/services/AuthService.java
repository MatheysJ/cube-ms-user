package com.cube.user.services;

import com.cube.user.clients.asaas.AsaasClient;
import com.cube.user.dtos.internal.asaas.response.CreateCustomerResponse;
import com.cube.user.exceptions.ConflitException;
import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.dtos.request.RequestLogin;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.internal.RequestValidate;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.mappers.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AsaasClient asaasClient;
    private final UserMapper userMapper;

    public ResponseUser register(RequestUser requestUser) {
        log.info("Starting user register validation");
        if (this.userService.getUserByMail(requestUser.getMail()).isPresent()) {
            throw new ConflitException(ExceptionCode.ALREADY_EXISTS);
        }

        log.info("Starting User password encryption");
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestUser.getPassword());

        log.info("Starting customer creation on Asaas");
        CreateCustomerResponse asaasCustomer = asaasClient.createCustomer(userMapper.requestToAsaas(requestUser));

        log.info("Starting to save User in database");
        requestUser.setPassword(encryptedPassword);
        ResponseUser savedUser = userService.createUser(requestUser, asaasCustomer.getId());

        log.info("User successfully saved in Database");
        return savedUser;
    }

    public String login(RequestLogin requestLogin) {
        log.info("Starting user token generation");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                requestLogin.getMail(),
                requestLogin.getPassword()
        );

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((UserDetails) auth.getPrincipal());
    }

    public String validate(RequestValidate requestValidate) {
        log.info("Starting user token validation");

        String subject = this.tokenService.validateToken(requestValidate.getToken());

        log.info("User token validated successfully");
        return subject;
    }

    private String createAccessTokenCookie(HttpServletRequest request, String token) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .maxAge(Duration.ofMinutes(30))
                .domain("localhost")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        return cookie.toString();
    }
    
    public HttpHeaders getAccessTokenHeaders(HttpServletRequest request, String token) {
        String cookie = createAccessTokenCookie(request, token);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie);

        return headers;
    }

}
