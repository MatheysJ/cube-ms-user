package com.cube.user.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {
    private String id;

    private String name;

    private String mail;

    private String profilePicture;
}
