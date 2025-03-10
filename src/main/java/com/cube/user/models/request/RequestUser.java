package com.cube.user.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "INVALID_MAIL")
    private String mail;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!#%*?&])[A-Za-z\\d@$!%#*?&]{8,20}$",
            message = "INVALID_PASSWORD")
    private String password;

    @NotNull
    private String profilePicture;
}
