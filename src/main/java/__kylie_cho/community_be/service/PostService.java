package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(newTitle);
        post.setContent(newContent);
        postRepository.save(post);
    }
}
