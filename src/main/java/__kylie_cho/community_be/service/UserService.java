package __kylie_cho.community_be.service;

import __kylie_cho.community_be.dto.UserChangePwRequestDto;
import __kylie_cho.community_be.dto.UserLoginRequestDto;
import __kylie_cho.community_be.dto.UserRegisterRequestDto;
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
import java.util.UUID;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User registerUser(UserRegisterRequestDto dto) throws IOException {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 프로필 이미지 처리
        String imageUrl;
        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            String folderPath = "uploaded_images/";
            String fileName = UUID.randomUUID().toString() + "_" + dto.getProfileImage().getOriginalFilename();
            Path path = Paths.get(folderPath + fileName);
            Files.createDirectories(path.getParent());
            dto.getProfileImage().transferTo(path);

            imageUrl = "/uploaded_images/" + fileName;
            System.out.println("📸 프로필 이미지 저장됨: " + imageUrl);
        } else {
            imageUrl = "https://blog.kakaocdn.net/dn/bCXLP7/btrQuNirLbt/N30EKpk07InXpbReKWzde1/img.png";
            System.out.println("⚠️ 프로필 이미지 없음, 기본 이미지 사용");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        user.setPassword(dto.getPassword());
        user.setProfileImage(imageUrl);

        return userRepository.save(user);
    }

    // 로그인
    public User loginUser(UserLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다!");
        }
        return user;
    }

    // 회원정보 조회
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 회원정보 수정
    @Transactional
    public User updateUser(Long id, String newNickname, MultipartFile newProfileImage) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (newNickname != null && !newNickname.isEmpty()) {
            user.setNickname(newNickname);
        }

        // 새로운 프로필 이미지 처리
        if (newProfileImage != null && !newProfileImage.isEmpty()) {
            String folderPath = "uploaded_images/";
            String fileName = UUID.randomUUID().toString() + "_" + newProfileImage.getOriginalFilename();
            Path path = Paths.get(folderPath + fileName);
            Files.createDirectories(path.getParent());
            newProfileImage.transferTo(path);

            user.setProfileImage(folderPath + fileName);
        }

        return userRepository.save(user);
    }

    // 비밀번호 수정
    @Transactional
    public User updatePassword(Long id, UserChangePwRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(dto.getOldPassword())) {
            throw new RuntimeException("기존 비밀번호가 아닙니다!");
        }

        user.setPassword(dto.getNewPassword());
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

