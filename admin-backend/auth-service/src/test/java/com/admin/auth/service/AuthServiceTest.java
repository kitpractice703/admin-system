package com.admin.auth.service;

import com.admin.auth.dto.request.LoginRequest;
import com.admin.auth.dto.response.LoginResponse;
import com.admin.auth.entity.AdminRole;
import com.admin.auth.entity.AdminUser;
import com.admin.auth.entity.UserStatus;
import com.admin.auth.exception.InvalidCredentialsException;
import com.admin.auth.repository.AdminUserRepository;
import com.admin.auth.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AdminUserRepository adminUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("정상 로그인 시 토큰 반환")
    void loginSuccess() {
        AdminUser user = AdminUser.builder()
                .email("admin@test.com")
                .password("hashedPassword")
                .name("관리자")
                .role(AdminRole.SUPER_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
        when(jwtTokenProvider.generateToken("admin@test.com", "SUPER_ADMIN")).thenReturn("mocked.jwt.token");

        LoginRequest request = new LoginRequest("admin@test.com","password123");
        LoginResponse response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("mocked.jwt.token");
        assertThat(response.getEmail()).isEqualTo("admin@test.com");
    }

    @Test
    @DisplayName("존재하지 않는 이메일일 경우 예외 발생")
    void loginFailByEmail() {
        when(adminUserRepository.findByEmail("none@test.com")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest("none@test.com", "password123");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void loginFailByPassword() {
        AdminUser user = AdminUser.builder()
                .email("admin@test.com")
                .password("hashedPassword")
                .name("관리자")
                .role(AdminRole.SUPER_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        when(adminUserRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        LoginRequest request = new LoginRequest("admin@test.com", "wrongPassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }


}
