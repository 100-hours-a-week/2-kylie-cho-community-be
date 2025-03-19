package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User registerUser(String email, String nickname, String password, MultipartFile profileImage) throws IOException {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // 프로필 이미지 처리
        String imageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            String folderPath = "uploaded_images/";
            Path path = Paths.get(folderPath + profileImage.getOriginalFilename());
            Files.createDirectories(path.getParent());
            profileImage.transferTo(path);
            imageUrl = path.toString();
        } else {
            imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.blog.naver.com%2Fgambasg%2F222132751279&psig=AOvVaw0chHekwVUVVVtatmZ20ZEX&ust=1742380007472000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLiF4Mm1k4wDFQAAAAAdAAAAABAE";
        }

        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setProfileImage(imageUrl);

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
    public User updateUser(Long id, String newNickname, MultipartFile newProfileImage) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (newNickname != null && !newNickname.isEmpty()) {
            user.setNickname(newNickname);
        }

        // 새로운 프로필 이미지 처리
        String imageUrl = user.getProfileImage();
        if (newProfileImage != null && !newProfileImage.isEmpty()) {
            String folderPath = "uploaded_images/";
            Path path = Paths.get(folderPath + newProfileImage.getOriginalFilename());
            Files.createDirectories(path.getParent());
            newProfileImage.transferTo(path);
            imageUrl = path.toString();
        } else {
            if (imageUrl == null || imageUrl.isEmpty()) {
                imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.blog.naver.com%2Fgambasg%2F222132751279&psig=AOvVaw0chHekwVUVVVtatmZ20ZEX&ust=1742380007472000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLiF4Mm1k4wDFQAAAAAdAAAAABAE";
            }
        }

        user.setProfileImage(imageUrl);

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
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // 프로필 이미지 저장소에서 삭제
        String profileImage = user.getProfileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            Path imagePath = Paths.get(profileImage);
            File imageFile = imagePath.toFile();

            // 저장소에 파일이 존재하면 삭제
            if (imageFile.exists()) {
                boolean isDeleted = imageFile.delete();
                if (!isDeleted) {
                    throw new RuntimeException("프로필 이미지 삭제에 실패했어요.");
                }
            }
        }

        userRepository.deleteById(id);
    }
}
