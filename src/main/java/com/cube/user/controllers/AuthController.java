package com.cube.user.controllers;

import com.cube.user.models.request.RequestLogin;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseUser> register(@RequestBody @Valid RequestUser body)  {

        ResponseUser responseUser = authService.register(body);

        return ResponseEntity.ok().body(responseUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUser> login(@RequestBody @Valid RequestLogin body)  {
        log.info("Starting login with body [ {} ]", body.toString());

        authService.login(body);

        log.info("Logged successfully");
        return ResponseEntity.ok().build();
    }
}
