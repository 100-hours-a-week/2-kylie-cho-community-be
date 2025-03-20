package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String newTitle,
            @RequestParam(required = false) String newContent,
            @RequestParam(required = false) MultipartFile newImageFile,
            @RequestParam Long userId
            ) throws IOException {

        Post updatedPost = postService.updatePost(id, newTitle, newContent, newImageFile, userId);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam Long userId,
            @RequestParam(required = false) MultipartFile image
            ) throws IOException {

        Post createdPost = postService.createPost(title, content, userId, image);

        return ResponseEntity.status(201).body(createdPost);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }
}
