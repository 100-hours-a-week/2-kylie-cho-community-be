package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.dto.UserChangePwRequestDto;
import __kylie_cho.community_be.dto.UserLoginRequestDto;
import __kylie_cho.community_be.dto.UserRegisterRequestDto;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @ModelAttribute UserRegisterRequestDto requestDto) throws IOException {



        User newUser = userService.registerUser(requestDto);
        return ResponseEntity.status(201).body(newUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequestDto requestDto) {
        User user = userService.loginUser(requestDto);
        return ResponseEntity.ok(user);
    }

    // 회원정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // 회원정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<User> updatedUser(
            @PathVariable Long id,
            @RequestPart(required = false) String nickname,
            @RequestPart(required = false) MultipartFile profileImage) throws IOException {

        User updatedUser = userService.updateUser(id, nickname, profileImage);
        return ResponseEntity.ok(updatedUser);      // 200 OK
    }

    // 비밀번호 수정
    @PutMapping("/{id}/change-password")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody UserChangePwRequestDto requestDto) {
        User updatedUser = userService.updatePassword(id, requestDto);
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
