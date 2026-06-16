package com.admin.auth.dto.response;

import com.admin.auth.entity.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String email;
    private String name;
    private AdminRole role;
}
