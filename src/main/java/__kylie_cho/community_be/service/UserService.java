package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    // 로그인 기능: 이메일과 비밀번호를 확인하여 사용자 반환
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    // 회원정보 수정
    @Transactional
    public void updateUser(Long id, String newNickname, String newProfileImage) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(newNickname);
        user.setProfileImage(newProfileImage);
        userRepository.save(user);
    }

    // 비밀번호 수정
    @Transactional
    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // 이메일 중복 확인 (회원가입 시 사용)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
