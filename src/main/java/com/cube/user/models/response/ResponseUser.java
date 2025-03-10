package com.cube.user.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {
    private String id;

    private String name;

    private String mail;

    private String password;

    private String profilePicture;
}
