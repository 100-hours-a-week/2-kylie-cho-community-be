package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestParam String email,
            @RequestParam String nickname,
            @RequestParam String password,
            @RequestParam MultipartFile profileImage) throws IOException {

        User newUser = userService.registerUser(email, nickname, password, profileImage);

        return ResponseEntity.status(201).body(newUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password);
        return ResponseEntity.ok(user);
    }

    // 회원정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<User> updatedUser(
            @PathVariable Long id,
            @RequestParam(required = false) String newNickname,
            @RequestParam(required = false) MultipartFile newProfileImage) throws IOException {

        User updatedUser = userService.updateUser(id, newNickname, newProfileImage);

        return ResponseEntity.ok(updatedUser);      // 200 OK
    }

    // 비밀번호 수정
    @PutMapping("/{id}/change-password")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User updatedUser = userService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(updatedUser);      // 200 OK
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
