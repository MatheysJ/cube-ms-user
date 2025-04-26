package com.cube.user.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerResponse {
    private String object;

    private String id;

    private String dateCreated;

    private String name;

    private String email;

    private String phone;

    private String mobilePhone;

    private String address;

    private String addressNumber;

    private String complement;

    private String province;

    private String city;

    private String cityName;

    private String state;

    private String country;

    private String postalCode;

    private String cpfCnpj;

    private String personType;

    private boolean deleted;

    private String additionalEmails;

    private String externalReference;

    private boolean notificationDisabled;

    private String observations;

    private boolean foreignCustomer;
}
