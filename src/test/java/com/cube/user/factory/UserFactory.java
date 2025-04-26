package com.cube.user.factory;

import com.cube.user.models.InternalUser;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.response.ResponseUser;
import com.cube.user.utils.JsonUtils;

public class UserFactory {
    static private final String idMock = "123";
    static private final String mailMock = "abc@abc";
    static private final String nameMock = "Test User";
    static private final String passwordMock = "Abcdef@123";
    static private final String asaasIdMock = "456";

    static public ResponseUser getMockOfResponseUser () {
        return ResponseUser.builder()
                .id(idMock)
                .mail(mailMock)
                .name(nameMock)
                .build();
    }

    static public RequestUser getMockOfRequestUser () {
        return RequestUser.builder()
                .mail(mailMock)
                .name(nameMock)
                .password(passwordMock)
                .build();
    }

    static public String getMockOfRequestUserAsJson () {
        return JsonUtils.toJson(getMockOfRequestUser());
    }

    static public RequestUser getMockOfInvalidRequestUser () {
        RequestUser requestUser = getMockOfRequestUser();
        requestUser.setPassword("123");

        return requestUser;

    }

    static public InternalUser getMockOfInternalUser () {
        return InternalUser.builder()
                .id(Long.parseLong(idMock))
                .mail(mailMock)
                .name(nameMock)
                .password(passwordMock)
                .build();
    }

    static public String getMockOfInvalidRequestUserAsJson () {
        return JsonUtils.toJson(getMockOfInvalidRequestUser());
    }

    static public String getMockOfAsaasId () {
        return asaasIdMock;
    }
}
