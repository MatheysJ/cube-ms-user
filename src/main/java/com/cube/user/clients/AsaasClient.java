package com.cube.user.clients;


import com.cube.user.dtos.internal.asaas.request.CreateCustomerBody;
import com.cube.user.dtos.internal.asaas.response.CreateCustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("asaas")
public interface AsaasClient {

    @PostMapping(value = "/v3/customers")
    CreateCustomerResponse createCustomer(@RequestHeader("access_token") String accessToken, @RequestBody CreateCustomerBody body);

}
