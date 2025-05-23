package com.cube.user.controllers;

import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.response.InternalResponseUser;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        log.info("Getting all users");
        List<ResponseUser> users = userService.getAllUsers();

        log.info("Successfully got all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternalResponseUser> getUserById(@PathVariable("id") Long id) {
        log.info("Start getting user by id");

        InternalResponseUser user = userService.getUserById(id);

        log.info("Successfully got user by id");
        return ResponseEntity.ok().body(user);

    }

    @PostMapping()
    public ResponseEntity<ResponseUser> createUser(
            @RequestHeader("x_customer_asaas_id") @Valid String asaasId,
            @RequestBody @Valid RequestUser body
    )  {
        log.info("Start creating user");

        ResponseUser user = userService.createUser(body, asaasId);

        log.info("Successfully created user");
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUser> editUser(@PathVariable("id") Long id, @RequestBody RequestUser body) {
        log.info("Start editing user");

        ResponseUser user = userService.editUserById(id, body);

        log.info("Successfully edited user");
        return ResponseEntity.ok().body(user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        log.info("Start deleting user");
        userService.deleteUserById(id);

        log.info("Successfully deleted user");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
