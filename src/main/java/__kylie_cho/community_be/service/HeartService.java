package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.Heart;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.HeartRepository;
import __kylie_cho.community_be.repository.PostRepository;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final EntityManager entityManager;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 여부 확인
    public boolean existsHeart(Long userId, Long postId) {
        return heartRepository.existsByUserIdAndPostId(userId, postId);
    }

    // 좋아요 누르기
    @Transactional
    public void heartPost(Long userId, Long postId) {
        if (!existsHeart(userId, postId)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            Heart heart = new Heart();
            heart.setUser(user);
            heart.setPost(post);
            heartRepository.save(heart);
        }
    }

    // 좋아요 취소
    @Transactional
    public void unheartPost(Long userId, Long postId) {
        heartRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
