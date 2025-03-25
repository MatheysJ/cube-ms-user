package com.cube.user.controllers;

import com.cube.user.models.request.RequestLogin;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.request.RequestValidate;
import com.cube.user.models.response.ResponseLogin;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.models.response.ResponseValidate;
import com.cube.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUser> register(@RequestBody @Valid RequestUser body)  {
        log.info("Starting register");
        ResponseUser responseUser = authService.register(body);

        log.info("Registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody @Valid RequestLogin body)  {
        log.info("Starting login");

        String token = authService.login(body);

        log.info("Logged in successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseLogin(token));
    }

    @PostMapping("/validate")
    public ResponseEntity<ResponseValidate> validate(@RequestBody @Valid RequestValidate body)  {
        log.info("Starting token validation");

        String username = authService.validate(body);

        log.info("Validated successfully");
        return ResponseEntity.ok( new ResponseValidate(username));
    }
}
