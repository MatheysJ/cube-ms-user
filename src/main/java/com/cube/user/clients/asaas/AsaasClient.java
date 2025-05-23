package com.cube.user.clients.asaas;


import com.cube.user.clients.asaas.config.AsaasClientConfig;
import com.cube.user.dtos.internal.asaas.request.CreateCustomerBody;
import com.cube.user.dtos.internal.asaas.response.CreateCustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "asaas", configuration = AsaasClientConfig.class)
public interface AsaasClient {

    @PostMapping(value = "/v3/customers")
    CreateCustomerResponse createCustomer(@RequestBody CreateCustomerBody body);

}
