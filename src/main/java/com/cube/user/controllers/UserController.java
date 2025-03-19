package com.cube.user.controllers;

import com.cube.user.models.request.RequestUser;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        List<ResponseUser> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping()
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser body)  {
        ResponseUser user = userService.createUser(body);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUser> editUser(@PathVariable("id") Long id, @RequestBody RequestUser body) {

        return userService.editUserById(id, body)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }

}
