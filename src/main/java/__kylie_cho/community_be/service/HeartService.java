package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.Heart;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.HeartRepository;
import __kylie_cho.community_be.repository.PostRepository;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public HeartService(HeartRepository heartRepository, PostRepository postRepository, UserRepository userRepository) {
        this.heartRepository = heartRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 좋아요 누르기
    @Transactional
    public Heart addHeart(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 사용자가 이미 좋아요를 눌렀는지 확인
        Optional<Heart> existingHeart = heartRepository.findByUserAndPost(user, post);
        if (existingHeart.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시글이에요!");
        }

        Heart heart = new Heart();
        heart.setUser(user);
        heart.setPost(post);

        // 좋아요수 증가
        post.incrementHeartCount();
        postRepository.save(post);

        return heartRepository.save(heart);
    }

    // 좋아요 삭제
    @Transactional
    public void removeHeart(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Heart heart = heartRepository.findByUserAndPost(user, post)
                        .orElseThrow(() -> new IllegalArgumentException("이 게시글에 좋아요를 누르지 않았어요."));

        heartRepository.delete(heart);

        // 좋아요수 감소
        post.decrementHeartCount();
        postRepository.save(post);
    }

    // 특정 게시글에 대한 좋아요 수 조회
    public long countHearts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return heartRepository.countByPost(post);
    }
}
