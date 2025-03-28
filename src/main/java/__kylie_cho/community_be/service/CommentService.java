package __kylie_cho.community_be.service;

import __kylie_cho.community_be.dto.CommentDto;
import __kylie_cho.community_be.entity.Comment;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.CommentRepository;
import __kylie_cho.community_be.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 특정 게시글에 대한 댓글수 조회
    public long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    // 특정 게시글에 대한 댓글 조회
    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않아요. ID: " + postId));

        return commentRepository.findByPostOrderByCreatedAtDesc(post)
                .stream()
                .map(CommentDto::new)
                .toList();
    }

    // 댓글 작성
    @Transactional
    public CommentDto createComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found. ID: " + postId));

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);

        // 댓글수 업데이트
        post.incrementCommentCount();
        postRepository.save(post);

        Comment savedComment = commentRepository.save(comment);
        return new CommentDto(savedComment);
    }

    // 댓글 수정
    @Transactional
    public CommentDto updateComment(Long id, String newContent, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found. ID: " + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 댓글을 수정할 수 있어요.");
        }

        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);
        return new CommentDto(updatedComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found. ID: " + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 댓글을 삭제할 수 있어요.");
        }

        // 댓글수 업데이트
        post.decrementCommentCount();
        postRepository.save(post);

        commentRepository.deleteById(id);
    }
}
