package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.dto.CommentCreateRequestDto;
import __kylie_cho.community_be.dto.CommentDto;
import __kylie_cho.community_be.dto.CommentUpdateRequestDto;
import __kylie_cho.community_be.entity.Comment;
import __kylie_cho.community_be.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 특정 게시글에 대한 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentCreateRequestDto requestDto) {
        CommentDto createdComment = commentService.createComment(requestDto.getPostId(), requestDto.getUserId(), requestDto.getContent());
        return ResponseEntity.status(201).body(createdComment);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id,
                                                    @RequestBody CommentUpdateRequestDto requestDto) {
        CommentDto updatedComment = commentService.updateComment(id, requestDto.getContent(), requestDto.getUserId());
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                              @RequestParam Long userId,
                                              @RequestParam Long postId) {
        commentService.deleteComment(id, userId, postId);
        return ResponseEntity.noContent().build();
    }
}
