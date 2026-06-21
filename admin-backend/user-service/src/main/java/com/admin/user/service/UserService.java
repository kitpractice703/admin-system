package com.admin.user.service;

import com.admin.user.dto.request.UserRequest;
import com.admin.user.dto.response.UserResponse;
import com.admin.user.entity.User;
import com.admin.user.exception.UserNotFoundException;
import com.admin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getPhoneNumber());
        return new UserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.update(request.getUsername(), request.getEmail(), request.getPhoneNumber());
        return new UserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
