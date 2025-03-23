package com.cube.user.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cube.user.factory.TokenFactory;
import com.cube.user.factory.UserFactory;
import com.cube.user.models.request.RequestLogin;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.request.RequestValidate;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    ResponseUser responseUser = UserFactory.getMockOfResponseUser();
    String requestUserAsJson = UserFactory.getMockOfRequestUserAsJson();
    String invalidRequestUserAsJson = UserFactory.getMockOfInvalidRequestUserAsJson();
    String requestLoginAsJson = TokenFactory.getMockOfRequestLoginAsJson();
    String requestValidateAsJson = TokenFactory.getMockOfRequestValidateAsJson();

    @Test()
    void shouldRegisterUserAndReturnCreated() throws Exception {
        Mockito.when(authService.register(Mockito.any(RequestUser.class))).thenReturn(responseUser);

        mockMvc.perform(
                post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestUserAsJson)
                ).andExpect(status().isCreated());
    }

    @Test()
    void shouldReturnBadRequestWhenRegisteringWithInvalidFields() throws Exception {
        Mockito.when(authService.register(Mockito.any(RequestUser.class))).thenReturn(responseUser);

        mockMvc.perform(
                post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestUserAsJson)
                ).andExpect(status().isBadRequest());
    }

    @Test()
    void shouldReturnCreatedWhenLoggingIn() throws Exception {
        Mockito.when(authService.login(Mockito.any(RequestLogin.class))).thenReturn(Mockito.anyString());

        mockMvc.perform(
                post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestLoginAsJson)
        ).andExpect(status().isCreated());
    }

    @Test()
    void shouldReturnOkAndUsernameWhenValidatingToken() throws Exception {
        Mockito.when(authService.validate(Mockito.any(RequestValidate.class))).thenReturn(Mockito.anyString());

        mockMvc.perform(
                post("/v1/auth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestValidateAsJson)
        ).andExpect(status().isOk());
    }

}
