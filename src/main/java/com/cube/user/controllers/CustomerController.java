package com.cube.user.controllers;

import com.cube.user.dtos.response.Customer;
import com.cube.user.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<Customer> getCustomer(
            @RequestHeader(value = "customer_cpfCnpj", required = false) String cpfCnpj,
            @RequestHeader(value = "customer_id", required = false) String customerId
    ) {
        log.info("Start getting user public info");

        Customer user = customerService.getCustomer(customerId, cpfCnpj);

        log.info("Successfully got user public info");
        return ResponseEntity.ok().body(user);

    }
}
