package com.cube.user.factory;

import com.cube.user.models.internal.InternalUser;
import com.cube.user.models.request.RequestUser;
import com.cube.user.models.response.ResponseUser;
import com.cube.user.utils.JsonUtils;

public class UserFactory {
    static private String idMock = "123";
    static private String mailMock = "abc@abc";
    static private String nameMock = "Test User";
    static private String profilePictureMock = "example.png";
    static private String passwordMock = "Abcdef@123";

    static public ResponseUser getMockOfResponseUser () {
        return ResponseUser.builder()
                .id(idMock)
                .mail(mailMock)
                .name(nameMock)
                .profilePicture(profilePictureMock)
                .build();
    }

    static public RequestUser getMockOfRequestUser () {
        return RequestUser.builder()
                .mail(mailMock)
                .name(nameMock)
                .profilePicture(profilePictureMock)
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
                .profilePicture(profilePictureMock)
                .password(passwordMock)
                .build();
    }

    static public String getMockOfInvalidRequestUserAsJson () {
        return JsonUtils.toJson(getMockOfInvalidRequestUser());
    }
}
