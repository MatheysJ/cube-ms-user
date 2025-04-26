package com.cube.user.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("asaas")
public interface AsaasClient {

    @PostMapping(value = "/v3/customers")
    void createUser();

}
