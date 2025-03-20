package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Comment;
import __kylie_cho.community_be.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 특정 게시글에 대한 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestParam Long postId,
            @RequestParam Long userId,
            @RequestParam String content
            ) {
        Comment createdComment = commentService.createComment(postId, userId, content);
        return ResponseEntity.status(201).body(createdComment);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                                 @RequestParam String content,
                                                 @RequestParam Long userId) {

        Comment updatedComment = commentService.updateComment(id, content, userId);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestParam Long userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.noContent().build();
    }
}
