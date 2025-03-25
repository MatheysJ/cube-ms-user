package com.cube.user.factory;

import com.cube.user.models.request.RequestLogin;
import com.cube.user.models.request.RequestValidate;
import com.cube.user.utils.JsonUtils;

public class TokenFactory {
    static public RequestLogin getMockOfRequestLogin() {
        return RequestLogin.builder().mail("abc@abc").password("Abcdef@123").build();
    }

    static public String getMockOfRequestLoginAsJson() {
        return JsonUtils.toJson(getMockOfRequestLogin());
    }

    static RequestValidate getMockOfRequestValidate() {
        return new RequestValidate("username");
    }

    static public String getMockOfRequestValidateAsJson() {
        return JsonUtils.toJson(getMockOfRequestValidate());
    }
}
