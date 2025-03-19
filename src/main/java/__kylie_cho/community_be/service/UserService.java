package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User registerUser(String email, String nickname, String password, String profileImage) {
        // 존재하는 사용자인지 확인
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);

        if (profileImage == null || profileImage.isEmpty()) {
            user.setProfileImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.blog.naver.com%2Fgambasg%2F222132751279&psig=AOvVaw0chHekwVUVVVtatmZ20ZEX&ust=1742380007472000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLiF4Mm1k4wDFQAAAAAdAAAAABAE");
        } else {
            user.setProfileImage(profileImage);
        }

        return userRepository.save(user);
    }

    // 로그인
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀렸습니다!");
        }
        return user;
    }

    // 회원정보 수정
    @Transactional
    public User updateUser(Long id, String newNickname, String newProfileImage) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(newNickname);

        if (newProfileImage == null || newProfileImage.isEmpty()) {
            user.setProfileImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.blog.naver.com%2Fgambasg%2F222132751279&psig=AOvVaw0chHekwVUVVVtatmZ20ZEX&ust=1742380007472000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLiF4Mm1k4wDFQAAAAAdAAAAABAE");
        } else {
            user.setProfileImage(newProfileImage);
        }

        return userRepository.save(user);
    }

    // 비밀번호 수정
    @Transactional
    public User updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("기존 비밀번호가 아닙니다!");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
