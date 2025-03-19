package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.PostRepository;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시글 목록 조회 (페이지네이션 지원)
    public List<Post> getPosts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return postRepository.findAll(pageable).getContent();
    }

    // 게시글 상세 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 게시글 수정
    @Transactional
    public Post updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        System.out.println("Before update - Title: " + post.getTitle() + ", Content: " + post.getContent());

        post.setTitle(newTitle);
        post.setContent(newContent);


        System.out.println("After update - Title: " + post.getTitle() + ", Content: " + post.getContent());
        return postRepository.save(post);
    }

    // 게시글 생성
    @Transactional
    public Post createPost(String title, String content, Long userId, String image) {
        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not Found"));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        if (image == null || image.isEmpty()) {
            post.setImage(null);
        } else {
            post.setImage(image);
        }

        post.setCommentCount(0);
        post.setHeartCount(0);
        post.setViewCount(0);

        return postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
