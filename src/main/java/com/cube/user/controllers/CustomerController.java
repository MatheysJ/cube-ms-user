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
    public ResponseEntity<Customer> getCurrentCustomer(@RequestHeader("customer_id") String customerId) {
        log.info("Start getting user public info by id");

        Customer user = customerService.getCustomerByCpfCnpj(customerId);

        log.info("Successfully got user public info by id");
        return ResponseEntity.ok().body(user);

    }
}
