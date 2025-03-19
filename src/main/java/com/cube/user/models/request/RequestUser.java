package com.cube.user.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String mail;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!#%*?&])[A-Za-z\\d@$!%#*?&]{8,20}$",
            message = "INVALID_PASSWORD")
    private String password;

    @NotBlank
    private String profilePicture;
}
