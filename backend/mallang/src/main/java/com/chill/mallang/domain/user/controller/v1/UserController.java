package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User API", description = "사용자 조회 API")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email")
    public Optional<User> getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/exists/email")
    public boolean checkEmailExists(@RequestParam String email) {
        return userService.existsByEmail(email);
    }

    @GetMapping("/exists/{id}")
    public boolean checkUserExistsById(@PathVariable Long id) {
        return userService.existsById(id);
    }
}
