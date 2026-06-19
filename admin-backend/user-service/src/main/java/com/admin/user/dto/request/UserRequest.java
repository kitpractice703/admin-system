package com.admin.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String phoneNumber;
}
