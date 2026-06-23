package com.admin.user.service;

import com.admin.user.dto.request.UserRequest;
import com.admin.user.dto.response.UserResponse;
import com.admin.user.entity.User;
import com.admin.user.exception.UserNotFoundException;
import com.admin.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("전체 유저 조회 시 목록 반환")
    void getAllUsers() {
        User user = new User("홍길동", "hong@test.com", "010-1234-5678");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("존재하는 ID로 조회 시 유저 반환")
    void getUserById() {
        User user = new User("홍길동", "hong@test.com", "010-1234-5678");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserById(1L);

        assertThat(result.getEmail()).isEqualTo("hong@test.com");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외 발생")
    void getUserByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("유저 생성 시 저장된 유저 반환")
    void createUser() {
        UserRequest request = new UserRequest();
        User user = new User("홍길동", "hong@test.com", "010-1234-5678");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.createUser(request);

        assertThat(result.getUsername()).isEqualTo("홍길동");
    }
    @Test
    @DisplayName("존재하지 않는 ID 삭제 시 예외 발생")
    void deleteUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
