package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping
    public List<Post> getPosts(@RequestParam(defaultValue = "0") Integer offset,
                               @RequestParam(defaultValue = "10") Integer limit) {
        return postService.getPosts(offset, limit);
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post.getTitle(), post.getContent());
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post postRequest) {
        Post createdPost = postService.createPost(postRequest.getTitle(), postRequest.getContent(), postRequest.getUser().getId(), postRequest.getImage());

        return ResponseEntity.status(201).body(createdPost);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
