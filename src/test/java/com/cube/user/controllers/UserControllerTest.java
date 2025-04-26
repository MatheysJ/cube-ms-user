package com.cube.user.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cube.user.factory.UserFactory;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    ResponseUser mockResponseUser = UserFactory.getMockOfResponseUser();
    String mockRequestUserAsJson = UserFactory.getMockOfRequestUserAsJson();

    @Test
    void shouldReturnAllUsers () throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(mockResponseUser));

        mockMvc.perform(
                get("/v1/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()
        );
    }

    @Test
    void shouldReturnUserById () throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(mockResponseUser);

        mockMvc.perform(
                get("/v1/user/123").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()
        );
    }

    @Test
    void shouldReturnCreatedWithDataWhenCreatingAnUser () throws Exception {
        Mockito.when(userService.createUser(Mockito.any(RequestUser.class), Mockito.anyString())).thenReturn(mockResponseUser);

        mockMvc.perform(
                post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequestUserAsJson)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldReturnOkWithNewUserDataWhenEditingAnUser () throws Exception {
        Mockito.when(userService.editUserById(Mockito.anyLong(), Mockito.any(RequestUser.class)))
                .thenReturn(mockResponseUser);

        mockMvc.perform(
                put("/v1/user/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequestUserAsJson)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnNoContentWhenDeletingUserById () throws Exception {
        mockMvc.perform(
                delete("/v1/user/123")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}
