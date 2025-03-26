package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.service.CommentService;
import __kylie_cho.community_be.service.HeartService;
import __kylie_cho.community_be.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final HeartService heartService;

    public PostController(PostService postService, CommentService commentService, HeartService heartService) {
        this.postService = postService;
        this.commentService = commentService;
        this.heartService = heartService;
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
        // 조회수 증가
        long viewCount = postService.incrementViewCount(id);

        // 댓글수 조회
        long commentCount = commentService.getCommentCount(id);

        // 좋아요수 조회
        long heartCount = heartService.countHearts(id);

        Post post = postService.getPostById(id);

        return ResponseEntity.ok()
                .header("viewCount", String.valueOf(viewCount))
                .header("commentCount", String.valueOf(commentCount))
                .header("heartCount", String.valueOf(heartCount))
                .body(post);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam Long userId
            ) throws IOException {

        Post updatedPost = postService.updatePost(id, title, content, imageFile, userId);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam Long userId,
            @RequestParam(required = false) MultipartFile imageFile
            ) throws IOException {

        Post createdPost = postService.createPost(title, content, userId, imageFile);

        return ResponseEntity.status(201).body(createdPost);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }
}
