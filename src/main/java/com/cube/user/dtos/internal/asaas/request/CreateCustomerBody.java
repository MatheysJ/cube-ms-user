package com.cube.user.dtos.internal.asaas.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerBody {

    private String name;

    private String cpfCnpj;

    private String email;

    private String phone;

    private String mobilePhone;

    private String address;

    private String addressNumber;

    private String complement;

    private String province;

    private String postalCode;

    private String externalReference;

    private boolean notificationDisabled;

    private String additionalEmails;

    private String municipalInscription;

    private String stateInscription;

    private String observations;

    private String groupName;

    private String company;

    private boolean foreignCustomer;

}
