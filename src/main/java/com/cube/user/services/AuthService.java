package com.cube.user.services;

import com.cube.user.clients.asaas.AsaasClient;
import com.cube.user.dtos.internal.asaas.request.CreateCustomerBody;
import com.cube.user.dtos.internal.asaas.response.CreateCustomerResponse;
import com.cube.user.exceptions.ConflitException;
import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.dtos.request.RequestLogin;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.internal.RequestValidate;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.mappers.UserMapper;
import com.cube.user.models.InternalUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        checkEmail(requestUser);
        checkCpfCnpj(requestUser);

        log.info("Starting User password encryption");
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestUser.getPassword());

        log.info("Starting customer creation on Asaas");
        CreateCustomerBody asaasBody = userMapper.requestToAsaas(requestUser);
        CreateCustomerResponse asaasCustomer = asaasClient.createCustomer(asaasBody);

        log.info("Starting to save User in database");
        requestUser.setPassword(encryptedPassword);
        ResponseUser savedUser = userService.createUser(requestUser, asaasCustomer.getId());

        log.info("User successfully saved in Database");
        return savedUser;
    }

    public String login(RequestLogin requestLogin) {
        log.info("Starting user login");
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                requestLogin.getMail(),
                requestLogin.getPassword()
        );

        log.info("Starting user token authentication");
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        log.info("Starting user token generation");
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object principal = auth.getPrincipal();
        return tokenService.generateToken((InternalUser) principal);
    }

    public String validate(RequestValidate requestValidate) {
        log.info("Starting user token validation");

        String subject = this.tokenService.validateToken(requestValidate.getToken());

        log.info("User token validated successfully");
        return subject;
    }

    public HttpHeaders getAccessTokenHeaders(String token) {
        String cookie = createAccessTokenCookie(token, Duration.ofMinutes(30));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie);

        return headers;
    }

    public HttpHeaders getCookieHeaderToRemoveToken() {
        String cookie = createAccessTokenCookie("", Duration.ofSeconds(0));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie);

        return headers;
    }

    private String createAccessTokenCookie(String token, Duration maxAge) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .maxAge(maxAge)
                .domain("matheus-junior.xyz")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        return cookie.toString();
    }

    private void checkEmail (RequestUser requestUser) {
        log.info("Checking if mail already exists");
        if (this.userService.getUserByMail(requestUser.getMail()).isPresent()) {
            throw new ConflitException(ExceptionCode.MAIL_ALREADY_EXISTS);
        }
        log.info("Mail verification concluded");
    }

    private void checkCpfCnpj (RequestUser requestUser) {
        log.info("Checking if cpf or cnpj already exists");
        if (this.userService.getUserByCpfCnpj(requestUser.getCpfCnpj()).isPresent()) {
            throw new ConflitException(ExceptionCode.CPF_CNPJ_ALREADY_EXISTS);
        }
        log.info("Cpf or cnpj verification concluded");
    }

}
