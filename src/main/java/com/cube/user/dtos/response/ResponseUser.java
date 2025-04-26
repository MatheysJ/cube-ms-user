package com.cube.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {
    private String id;

    private String name;

    private String mail;

    private String phone;

    private String cpfCnpj;
}
