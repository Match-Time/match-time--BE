package com.matchTime.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/users")
    public Long save(@RequestBody UserCreateRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/users/{userId}/rooms/{roomId}")
    public void joinRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        userService.joinRoom(userId, roomId);
    }
}
